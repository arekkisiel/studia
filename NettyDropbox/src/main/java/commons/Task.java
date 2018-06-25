package commons;

import java.util.UUID;

public class Task {
    private int clientId;
    private UUID taskId;
    private String filename;

    public Task(int clientId, UUID taskId, String filename) {
        this.clientId = clientId;
        this.taskId = taskId;
        this.filename = filename;
    }

    public Task() {
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
