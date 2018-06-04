package com.company;

import java.io.IOException;
import java.util.LinkedList;

public class Handler {
    public static LinkedList<Task> taskList;

    Handler(){
        taskList = new LinkedList<>();
    }

    public static Task getTask(){
        return taskList.getFirst();
    }

    public static void addTask(Task task){
        taskList.push(task);
    }
}
