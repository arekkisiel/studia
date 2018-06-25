package com.company;

import java.io.*;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Client {
    public static int clientId;
    private static List<UUID> taskList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        String path = "E:\\repos\\studia\\MyDropbox\\Clients\\";
        Socket serverConnection = new Socket("127.0.0.1", 8000);
        Socket taskManagerConnection = new Socket("127.0.0.1", 8080);
        String pathPort = path + serverConnection.getLocalPort() + "\\";
        clientId = serverConnection.getLocalPort();

        try {
             Files.createDirectory(Paths.get(pathPort));
        }
        catch(FileAlreadyExistsException e) {
            Paths.get(pathPort);
        }

        Directory watcher = new Directory(pathPort);

        while(true){
            String watcherResponse = watcher.checkDirectory();
            if(Objects.nonNull(watcherResponse)) {
                Thread.sleep(100);
                taskList.add(queueTask(serverConnection.getLocalPort(), watcherResponse, taskManagerConnection));
                for(UUID taskId : taskList){
                    if(canPerform(taskId)){
                        String filename = getTask(taskId).filename;
                        sendFile(Paths.get(pathPort), filename, serverConnection);
                    }
                }
            }
        }
    }

    private static boolean canPerform(UUID taskId) {
        FileInputStream fis = new FileInputStream(String.valueOf(taskId)));

        DataOutputStream dos = new DataOutputStream(taskManagerConnection.getOutputStream());
        dos.writeUTF(String.valueOf(filename));

        return false;
    }

    private static UUID queueTask(int clientId, String filename, Socket taskManagerConnection) throws IOException {
        UUID taskId = UUID.randomUUID();
        FileInputStream fis = new FileInputStream(String.valueOf(new Task(clientId, taskId, filename)));

        DataOutputStream dos = new DataOutputStream(taskManagerConnection.getOutputStream());
        dos.writeUTF(String.valueOf(filename));

        int count;
        byte[] buffer = new byte[8192];
        while ((count = fis.read(buffer)) > 0)
            dos.write(buffer, 0, count);
        return taskId;
    }


    private static void sendFile(Path filepath, String filename, Socket s) throws IOException {
        FileInputStream fis = new FileInputStream(filepath.toString() + "\\" + filename);

        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        dos.writeUTF(String.valueOf(filename));

        int count;
        byte[] buffer = new byte[8192];
        while ((count = fis.read(buffer)) > 0)
            dos.write(buffer, 0, count);
    }
}
