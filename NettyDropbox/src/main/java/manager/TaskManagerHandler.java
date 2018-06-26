package manager;

import commons.task.Task;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TaskManagerHandler extends ChannelInboundHandlerAdapter {
    private static ConcurrentLinkedQueue<Task> allowedTasks;
    private ByteBuf tmp;

    private static ConcurrentHashMap<Integer, ConcurrentLinkedQueue<Task>> taskQueue;
    private static ConcurrentLinkedQueue<Integer> clientPriority;
    private static ConcurrentLinkedQueue<Integer> serverList;

    public TaskManagerHandler(ConcurrentHashMap<Integer, ConcurrentLinkedQueue<Task>> taskQueue,
                              ConcurrentLinkedQueue<Integer> clientPriority, ConcurrentLinkedQueue<Task> allowedTasks,
                              ConcurrentLinkedQueue<Integer> serverList){
        this.taskQueue = taskQueue;
        this.clientPriority = clientPriority;
        this.allowedTasks = allowedTasks;
        this.serverList = serverList;
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
        if(Objects.nonNull(requestData.getTaskId())){
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
        updateAllowedTasks();
        propagateAllowedTasks(ctx, clientId);
    }

    private static void propagateAllowedTasks(ChannelHandlerContext ctx, int clientId) {
        for(Task allowedTask : allowedTasks) {
            if(allowedTask.getClientId() == clientId) {
                int server = serverList.poll();
                allowedTask.setServerPort(server);
                serverList.add(server);
                ctx.channel().writeAndFlush(allowedTask);
                allowedTasks.remove(allowedTask);
            }
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
}
