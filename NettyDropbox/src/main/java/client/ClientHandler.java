package client;

import commons.Task;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private static ConcurrentLinkedQueue<Task> tasksToPerform;

    public ClientHandler(ConcurrentLinkedQueue<Task> tasksToPerform){
        this.tasksToPerform = tasksToPerform;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {
//
//        Task msg = new Task();
//        msg.setClientId(123);
//        msg.setTaskId(UUID.randomUUID());
//        msg.setFilename("aaa");
//        ChannelFuture future = ctx.writeAndFlush(msg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Task requestData = (Task) msg;
        tasksToPerform.add(requestData);
    }
}
