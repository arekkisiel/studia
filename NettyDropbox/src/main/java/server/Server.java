package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Server{
    private static Socket connection;


    public static void main(String[] args) throws IOException {
//        String path = "G:\\repos\\studia\\NettyDropbox\\Server\\";
        ServerSocket serverSocket = new ServerSocket(8000);
        while(true){
            connection = serverSocket.accept();
            Runnable connectionHandler = new ServerConnectionHandler(connection);
            new Thread(connectionHandler).start();
        }
    }
}