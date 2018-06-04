package com.company;

import javafx.util.Pair;

import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Task {
    OperationType operationType;
    Socket socket;
    String filename;

    Task(OperationType operationType, Socket socket, String filename){
        this.operationType = operationType;
        this.socket = socket;
        this.filename = filename;
    }
}

enum OperationType {
    CLIENT_DOWNLOAD, CLIENT_UPLOAD
}
