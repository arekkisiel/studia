package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Server{
    private static int clientId = 0;
    private static int port = 8000;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        registerToTaskManager();
        while(true){
            Socket connection = serverSocket.accept();
            initializeWorkspace(connection);
            Runnable runnable = () -> {
                try {
                    saveFile(connection);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            new Thread(runnable).start();

        }
    }

    private static void registerToTaskManager() {
        try {
            new Socket("localhost", 8081);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initializeWorkspace(Socket connection) throws IOException {
        DataInputStream dis = new DataInputStream(connection.getInputStream());
        try {
            clientId = dis.readInt();
            String path = "C:\\repos\\studia\\NettyDropbox\\Server\\" + clientId;
            Files.createDirectory(Paths.get(path));
        } catch (FileAlreadyExistsException e){
          //do nothing
        } catch (EOFException e){
            //do nothing
        }
    }

    private static void saveFile(Socket connection) throws IOException {
        String path = "C:\\repos\\studia\\NettyDropbox\\Server\\" + clientId + "\\";
        DataInputStream dis = new DataInputStream(connection.getInputStream());
        String filename = "";
        try {
             filename = dis.readUTF();
        } catch (EOFException e){
            //do nothing
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(new File(path + filename));
        } catch (FileNotFoundException e){
            //do nothing
        }
        int count;
        byte[] buffer = new byte[8192];
        while ((count = dis.read(buffer)) > 0)
            outputStream.write(buffer, 0, count);
    }
}