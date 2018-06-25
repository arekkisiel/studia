package com.company;

import java.util.UUID;

public class Task {
    int clientId;
    UUID taskId;
    String filename;

    public Task(int clientId, UUID taskId, String filename) {
        this.clientId = clientId;
        this.taskId = taskId;
        this.filename = filename;
    }
}
