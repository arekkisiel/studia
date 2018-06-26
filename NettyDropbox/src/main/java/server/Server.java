package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Server{

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        while(true){
            Socket connection = serverSocket.accept();
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

    private static void saveFile(Socket connection) throws IOException {
        String path = "G:\\repos\\studia\\NettyDropbox\\Server\\";
        String pathPort = path + 1000 + "\\";
        try {
            Files.createDirectory(Paths.get(pathPort));
        } catch (FileAlreadyExistsException e) {
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