package com.company;

import java.io.*;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Objects;

public class Client {
    private static Socket socket;
    private static String directoryPath;
    private static LinkedList<Task> taskList = new LinkedList<>();
    private static Directory watcher;

    public static void main(String path) throws IOException, InterruptedException {
        socket = new Socket("127.0.0.1", 8000);
        directoryPath = path + socket.getLocalPort() + "\\";
        watcher = new Directory(directoryPath);

        createDirs();

        new Thread(() -> {
            collectTasksFromDirectory();
        }).start();


        while(true){
            queryHandler();
            System.out.println("Socket connected: " + socket.isConnected());
            Thread.sleep(5000);
        }
    }

    private static void queryHandler() {

    }

    public static void createDirs(){
        try {
            Files.createDirectory(Paths.get(directoryPath));
        } catch (FileAlreadyExistsException e) {
            Paths.get(directoryPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//        while(true){
//            String watcherResponse = watcher.checkDirectory();
//            if(Objects.nonNull(watcherResponse))
//                Thread.sleep(100);
//                sendFile(Paths.get(pathPort), watcherResponse, socket);
//            System.out.println("Socket connected: " + socket.isConnected());
//            Thread.sleep(1000);
//        }
//    }


    public static void sendFile(String filename) throws IOException {
        FileInputStream fis = new FileInputStream(directoryPath + "\\" + filename);

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF(String.valueOf(filename));

        int count;
        byte[] buffer = new byte[8192];
        while ((count = fis.read(buffer)) > 0)
            dos.write(buffer, 0, count);
    }

    public static void collectTasksFromDirectory(){
        while(true){
            String watcherResponse = watcher.checkDirectory();
            if(Objects.nonNull(watcherResponse)) {
                taskList.push(new Task(OperationType.CLIENT_UPLOAD, socket, watcherResponse));
            }
        }
    }

    public static void uploadFile(String filename){
        while(true){
            String watcherResponse = watcher.checkDirectory();
            if(Objects.nonNull(watcherResponse)) {
                taskList.push(new Task(OperationType.CLIENT_UPLOAD, socket, watcherResponse));
            }
        }
    }
}
