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

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public class TaskManager {
    private static int port = 8080;
    public static ChannelFuture serverChannel;

    private static ConcurrentHashMap<Integer, ConcurrentLinkedQueue<Task>> taskQueue = new ConcurrentHashMap<>();
    private static ConcurrentLinkedQueue<Integer> clientPriority = new ConcurrentLinkedQueue<>();
    private static Semaphore semaphore = new Semaphore(5);
    private static ConcurrentLinkedQueue<Task> allowedTasks = new ConcurrentLinkedQueue<>();


    public TaskManager(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        establishServerChannel();
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
                        ch.pipeline().addLast(new TaskDecoder(), new TaskEncoder(), new TaskManagerHandler(taskQueue, clientPriority, allowedTasks));
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