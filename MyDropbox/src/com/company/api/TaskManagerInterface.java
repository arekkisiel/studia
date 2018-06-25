package com.company.api;

import com.company.Task;

import java.util.UUID;

public interface TaskManagerInterface {
    boolean canPerformTask(UUID taskId);
    void queueTask(Task newTask);
}
