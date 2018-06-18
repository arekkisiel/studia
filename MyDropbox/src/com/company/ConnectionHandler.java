package com.company;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ConnectionHandler implements Runnable {
    private static Socket connection;
    private static Semaphore semaphore;

    public ConnectionHandler(Socket clientSocket, Semaphore semaphore) {
        this.connection = clientSocket;
        this.semaphore = semaphore;
    }

//    26.06 godz 10 w pokoju

    @Override
    public void run() {
        while(true) {
            boolean permit = false;
            try {
                permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
                if (permit) {
                    System.out.println("Semaphore acquired.");
                    try {
                        saveFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (permit) {
                            semaphore.release();
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void saveFile() throws IOException {
        String path = "E:\\repos\\studia\\MyDropbox\\Server\\";
        String pathPort = path + connection.getPort() + "\\";
        try {
            Files.createDirectory(Paths.get(pathPort));
        } catch (FileAlreadyExistsException e) {
            Paths.get(pathPort);
            DataInputStream dis = new DataInputStream(connection.getInputStream());
            String filename = dis.readUTF();

            FileOutputStream outputStream = new FileOutputStream(new File(pathPort + filename));

            int count;
            byte[] buffer = new byte[8192];
            while ((count = dis.read(buffer)) > 0)
                outputStream.write(buffer, 0, count);
        }
    }
}
