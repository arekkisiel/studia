package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Server{

    public static void main(String[] args) throws IOException {
        String path = "E:\\repos\\studia\\MyDropbox\\Server\\";
        ServerSocket serverSocket = new ServerSocket(8000);
        while(true){
            Socket clientSocket = serverSocket.accept();
            Runnable connectionHandler = new ConnectionHandler(clientSocket);
            new Thread(connectionHandler).start();
        }

    }
}