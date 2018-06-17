package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Server{

    public static void main(String[] args) throws IOException {
        String path = "G:\\repos\\studia\\MyDropbox\\Server\\";
        ServerSocket serverSocket = new ServerSocket(8000);
        Semaphore semaphore = new Semaphore(5);

        while(true) {
            Socket clientSocket = serverSocket.accept();
            boolean permit = false;
            try {
                permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
                if (permit) {
                    System.out.println("Semaphore acquired.");
                    saveFile(clientSocket, path);
                }
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (permit) {
                    semaphore.release();
                    System.out.println("Semaphore released.");
                }
            }
        }
    }

    private static void saveFile(Socket clientSock, String path) throws IOException {
        String pathPort = path + clientSock.getPort() + "\\";
        try {
            Files.createDirectory(Paths.get(pathPort));
        } catch (FileAlreadyExistsException e) {
            Paths.get(pathPort);
            DataInputStream dis = new DataInputStream(clientSock.getInputStream());
            String filename = dis.readUTF();

            FileOutputStream outputStream = new FileOutputStream(new File(pathPort + filename));

            int count;
            byte[] buffer = new byte[8192];
            while ((count = dis.read(buffer)) > 0)
                outputStream.write(buffer, 0, count);
        }
    }
}