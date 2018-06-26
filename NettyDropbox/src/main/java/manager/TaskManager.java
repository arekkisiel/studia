package manager;

import commons.task.Task;
import commons.task.TaskDecoder;
import commons.task.TaskEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public class TaskManager {
    private static int port = 8080;
    public static ChannelFuture serverChannel;

    private static ConcurrentHashMap<Integer, ConcurrentLinkedQueue<Task>> taskQueue = new ConcurrentHashMap<>();
    private static ConcurrentLinkedQueue<Integer> clientPriority = new ConcurrentLinkedQueue<>();
    private static ConcurrentLinkedQueue<Task> allowedTasks = new ConcurrentLinkedQueue<>();
    private static ServerSocket taskManagerServer;
    private static ConcurrentLinkedQueue<Integer> serverList = new ConcurrentLinkedQueue<>();

    static {
        try {
            taskManagerServer = new ServerSocket(8081);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public TaskManager(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        establishServerChannel();

        Runnable acceptServers = () -> {
            while (true){
                try {
                    DataInputStream dis = new DataInputStream(taskManagerServer.accept().getInputStream());
                    serverList.add(dis.readInt());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(acceptServers).run();
    }


    private static void establishServerChannel() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch)
                            throws Exception {
                        ch.pipeline().addLast(new TaskDecoder(), new TaskEncoder(), new TaskManagerHandler(taskQueue, clientPriority, allowedTasks, serverList));
                    }
                }).option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            serverChannel = b.bind(port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}