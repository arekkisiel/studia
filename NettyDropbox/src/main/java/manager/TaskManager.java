package manager;

import commons.Task;
import commons.TaskDecoder;
import commons.TaskEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TaskManager {
    private static int port = 8080;
    public static ChannelFuture serverChannel;

    private static ConcurrentHashMap<Integer, ConcurrentLinkedQueue<Task>> taskQueue = new ConcurrentHashMap<>();
    private static ConcurrentLinkedQueue<Integer> clientPriority = new ConcurrentLinkedQueue<>();
    private static ConcurrentLinkedQueue<Task> allowedTasks = new ConcurrentLinkedQueue<>();


    public TaskManager(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        establishServerChannel();
        while (true){
            updateAllowedTasks();
            System.out.println("Allowed tasks: " + allowedTasks.size());

            System.out.println("Queued clients: " + taskQueue.size());
            Thread.sleep(2000);
        }
    }

    private static void updateAllowedTasks() {
        if(allowedTasks.size() < 10){
            for(int clientId : clientPriority){
                if(allowedTasks.size() < 10) {
                    Task tmpTask = taskQueue.get(clientId).poll();
                    if (Objects.nonNull(tmpTask)) {
                        allowedTasks.add(tmpTask);
                        int tmpClient = clientPriority.poll();
                        clientPriority.add(tmpClient);
                    }
                }
            }
        }
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
                        ch.pipeline().addLast(new TaskDecoder(), new TaskEncoder(), new TaskManagerHandler(taskQueue, clientPriority));
                        ch.pipeline().addLast(new TaskDecoder(), new TaskEncoder(), new TaskManagerHandler(taskQueue, clientPriority));
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