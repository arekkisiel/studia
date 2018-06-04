package com.company;

import sun.awt.image.ImageWatched;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class Server{
    public static Handler handler = new Handler();
    private static LinkedList<Socket> activeSockets = new LinkedList<>();
    private static ServerSocket serverSocket;
    private static String directoryPath;

    public static void main(String path) throws IOException, InterruptedException {
        directoryPath = path;
        serverSocket = new ServerSocket(8000);

        new Thread(() -> {
            acceptClients(serverSocket);
        }).start();

        new Thread(() -> {
            collectTasksFromClients();
        }).start();

        while(true){
            for(Socket activeSocket : activeSockets)
            {
                saveFile(activeSocket);
            }
            System.out.println("Server online");
            Thread.sleep(1000);
        }
    }

    private static void collectTasksFromClients() {

    }

    private static void acceptClients(ServerSocket serverSocket) {
        while(true){
            try {
                Socket tmpSocket = serverSocket.accept();
                createDirs(tmpSocket);
                activeSockets.push(tmpSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createDirs(Socket clientSocket){
        String pathPort = directoryPath + clientSocket.getPort() + "\\";
        try {
            Files.createDirectory(Paths.get(pathPort));
        }
        catch(FileAlreadyExistsException e) {
            Paths.get(pathPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        ServerSocket serverSocket = new ServerSocket(8000);
//
//        while(true){
//            Socket socket = serverSocket.accept();
//            saveFile(socket, path);
//            System.out.println("Server online");
//            Thread.sleep(1000);
//        }
    }

    public static void saveFile(Socket clientSocket) throws IOException {
        String pathPort = directoryPath + clientSocket.getPort() + "\\";
        DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
        String filename = dis.readUTF();

        FileOutputStream outputStream = new FileOutputStream(new File(pathPort + filename));

        int count;
        byte[] buffer = new byte[8192];
        while ((count = dis.read(buffer)) > 0)
            outputStream.write(buffer, 0, count);
        System.out.println("File transferred.");
    }
//
//    public Task collectTasksFromDirectory(Server server, Client client){
//        while(true){
//            String watcherResponse = server.watcher.checkDirectory();
//            if(Objects.nonNull(watcherResponse)) {
//                return new Task(OperationType.CLIENT_DOWNLOAD, client, server, watcherResponse);
//            }
//        }
//    }
}