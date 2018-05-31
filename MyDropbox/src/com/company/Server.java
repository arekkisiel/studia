package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Server{

    public static void main(String path) throws IOException, InterruptedException {

        ServerSocket serverSocket = new ServerSocket(8000);

        while(true){
            Socket socket = serverSocket.accept();
            saveFile(socket, path);
            System.out.println("Server online");
            Thread.sleep(1000);
        }
    }

    private static void saveFile(Socket clientSock, String path) throws IOException {
        String pathPort = path + clientSock.getPort() + "\\";
        try {
            Files.createDirectory(Paths.get(pathPort));
        }
        catch(FileAlreadyExistsException e) {
            Paths.get(pathPort);
        }

        DataInputStream dis = new DataInputStream(clientSock.getInputStream());
        String filename = dis.readUTF();

        FileOutputStream outputStream = new FileOutputStream(new File(pathPort + filename));

        int count;
        byte[] buffer = new byte[8192];
        while ((count = dis.read(buffer)) > 0)
            outputStream.write(buffer, 0, count);
    }
}