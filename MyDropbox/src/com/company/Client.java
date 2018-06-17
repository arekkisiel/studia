package com.company;

import java.io.*;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

public class Client {

    public static void main(String[] args) throws Exception {
        String path = "G:\\repos\\studia\\MyDropbox\\Clients\\";
        Socket socket = new Socket("127.0.0.1", 8000);
        String pathPort = path + socket.getLocalPort() + "\\";
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
                sendFile(Paths.get(pathPort), watcherResponse, socket);
            }
            System.out.println("Socket connected: " + socket.isConnected());
            Thread.sleep(1000);
        }
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
