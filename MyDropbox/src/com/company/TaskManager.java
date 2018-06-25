package com.company;

import com.company.api.TaskManagerInterface;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskManager implements Runnable, TaskManagerInterface {
    private static final int MAX_ALLOWED_TASKS = 5;
    public static List<Task> allowedTasksList = new ArrayList<>();
    public static List<Task> taskList = new ArrayList<>();
    public static List<Server> serverList = new ArrayList<>();
    public static List<Client> clientsList = new ArrayList<>();

    @Override
    public void run() {
        System.out.println("Collecting tasks.");
        try {
            while(true) {
                DataInputStream objectInputStream = new DataInputStream(socket.getInputStream());
                int clientId = objectInputStream.readInt();
                Task receivedTask = new Task(clientId, UUID.randomUUID());
                System.out.println("Received task: " + receivedTask.taskId);
                taskList.add(receivedTask);
                new DataOutputStream(socket.getOutputStream()).writeUTF(receivedTask.taskId.toString());
                Thread.sleep(1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean canPerformTask(UUID taskId) {
        for(Task task : taskList){
            if(task.taskId == taskId)
                return true;
        }
        return false;
    }

    @Override
    public void queueTask(Task newTask) {
        taskList.add(newTask);
    }

    public void allowTasks(){
        if(allowedTasksList.size() < MAX_ALLOWED_TASKS){
            for(Client client : clientsList){
                for(Task allowedTask : allowedTasksList){
                    if(allowedTask.clientId != client.clientId)
                        //add task from queued tasks for this client and put him at the end of the clientList
                        //remove allowed task from the queued tasks
                }
            }
        }
    }
}
