package manager;

import commons.Task;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TaskManagerHandler extends ChannelInboundHandlerAdapter {
    private ByteBuf tmp;
    private static ConcurrentHashMap<Integer, ConcurrentLinkedQueue<Task>> taskQueue;
    private static ConcurrentLinkedQueue<Integer> clientPriority;

    public TaskManagerHandler(ConcurrentHashMap<Integer, ConcurrentLinkedQueue<Task>> taskQueue, ConcurrentLinkedQueue<Integer> clientPriority){
        this.taskQueue = taskQueue;
        this.clientPriority = clientPriority;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        System.out.println("Handler added");
        tmp = ctx.alloc().buffer(4);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        System.out.println("Handler removed");
        tmp.release();
        tmp = null;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Task requestData = (Task) msg;
        int clientId = requestData.getClientId();
        try {
            taskQueue.get(clientId).add(requestData);
        } catch(NullPointerException e){
            clientPriority.add(clientId);
            taskQueue.put(clientId, new ConcurrentLinkedQueue());
            taskQueue.get(clientId).add(requestData);
        }
        System.out.println("Added new task. Queue size for client " + requestData.getClientId() + " is: " + taskQueue.get(requestData.getClientId()).size());
        System.out.println(requestData.getClientId() + " " + requestData.getTaskId().toString() + " " + requestData.getFilename());
    }

}
