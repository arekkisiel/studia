package client;

import commons.Directory;
import commons.Task;
import commons.TaskDecoder;
import commons.TaskEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Client {
    public static int clientId;
    public static ChannelFuture taskManagerChannel;
    private static ConcurrentLinkedQueue<Task> tasksToPerform = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) throws Exception {
        String path = "G:\\repos\\studia\\NettyDropbox\\Clients\\";
        Socket serverConnection = new Socket("127.0.0.1", 8000);
        String pathPort = path + serverConnection.getLocalPort() + "\\";
        clientId = serverConnection.getLocalPort();
        establishTaskManagerConnection();

        try {
            Files.createDirectory(Paths.get(pathPort));
        }
        catch(FileAlreadyExistsException e) {
            Paths.get(pathPort);
        }

        Directory watcher = new Directory(pathPort);

        while(true){
            String watcherResponse = watcher.checkDirectory();
            System.out.println("Detected new item.");
            if(Objects.nonNull(watcherResponse)) {
                Thread.sleep(100);
                System.out.println("Sending new task to manager.");
                sendTaskToManager(watcherResponse);

                Task newTask = new Task(clientId, UUID.randomUUID(), watcherResponse);
                tasksToPerform.add(newTask);
            }
            if(tasksToPerform.size() > 0){
                Task tmpTask = tasksToPerform.poll();
                sendFileToServer(Paths.get(pathPort), tmpTask.getFilename(), serverConnection);
            }
        }

    }

    private static void establishTaskManagerConnection() throws InterruptedException {
        String host = "localhost";
        int port = 8080;

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(new ChannelInitializer<SocketChannel>() {

            @Override
            public void initChannel(SocketChannel ch)
                    throws Exception {
                ch.pipeline().addLast(new TaskEncoder(), new TaskDecoder(), new ClientHandler(tasksToPerform));
            }
        });

        taskManagerChannel = b.connect(host, port).sync();
    }


    private static void sendTaskToManager(String filename) throws InterruptedException {
        Task newTask = new Task(clientId, UUID.randomUUID(), filename);
        taskManagerChannel.channel().writeAndFlush(newTask);
    }

    private static void sendFileToServer(Path filepath, String filename, Socket s) throws IOException {
        FileInputStream fis = new FileInputStream(filepath.toString() + "\\" + filename);

        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        dos.writeUTF(String.valueOf(filename));

        int count;
        byte[] buffer = new byte[8192];
        while ((count = fis.read(buffer)) > 0)
            dos.write(buffer, 0, count);
    }
}
