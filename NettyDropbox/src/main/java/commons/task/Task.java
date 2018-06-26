package commons.task;

import java.net.ServerSocket;
import java.util.UUID;

public class Task {
    private int clientId;
    private UUID taskId;
    private String filename;
    private int serverPort;
    private boolean isUpload;

    public Task(int clientId, UUID taskId, String filename, boolean type) {
        this.clientId = clientId;
        this.taskId = taskId;
        this.filename = filename;
        this.isUpload = type;
    }

    public Task(int clientId, UUID taskId, String filename, int serverPort, boolean type) {
        this.clientId = clientId;
        this.taskId = taskId;
        this.filename = filename;
        this.serverPort = serverPort;
        this.isUpload = type;
    }

    public Task() {
    }

    public Task(int clientId) {
        this.clientId = clientId;
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

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public boolean isUpload() {
        return isUpload;
    }

    public void setUpload(boolean upload) {
        isUpload = upload;
    }
}
