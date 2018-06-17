package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskManager implements Runnable {
    public static List<Task> taskList = new ArrayList<>();
    private static Socket socket;

    TaskManager(Socket socket){
        this.socket = socket;
    }
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
}
