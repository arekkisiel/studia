package com.company;

import java.util.UUID;

public class Task {
    int clientId;
    UUID taskId;

    public Task(int clientId, UUID taskId) {
        this.clientId = clientId;
        this.taskId = taskId;
    }
}
