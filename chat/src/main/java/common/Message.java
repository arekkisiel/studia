package common;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Message {

    private String message;
    private String username;
    private List<Pair<String, Boolean>> sent;

    public Message(String message, String username) {
        this.message = message;
        this.username = username;
        this.sent = new ArrayList<>();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isSent(String username) {
        for(Pair entry : this.sent){
            if(entry.getKey().toString().contains(username))
                return (boolean) entry.getValue();
        }
        return false;
    }

    public void setSent(String username) {
        this.sent.remove(new Pair<>(username, false));
        this.sent.add(new Pair<>(username, true));
    }
}
