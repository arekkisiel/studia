package server;

import com.caucho.hessian.server.HessianServlet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import common.HandlerInterface;
import common.Message;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HandlerImpl extends HessianServlet implements HandlerInterface {

    private ArrayList<Message> messages = new ArrayList<>();
    Gson gson = new Gson();

    @Override
    public String fetchConversation(String username) {
        Type listType = new TypeToken<ArrayList<Message>>() {}.getType();
        for(Message message : messages)
            message.setSent(username);
        return gson.toJson(messages, listType);
    }

    @Override
    public boolean isNewMessage(String username) {
        for(Message message : messages){
            if(!message.isSent(username))
                return true;
        }
        return false;
    }

    @Override
    public String fetchNewMessages(String username) {
        Type listType = new TypeToken<ArrayList<Message>>() {}.getType();
        ArrayList<Message> newMessages = new ArrayList<>();
        for(Message message : messages){
            if(!message.isSent(username)) {
                newMessages.add(message);
                message.setSent(username);
            }
        }
        return gson.toJson(newMessages, listType);
    }

    @Override
    public void sendMessage(String messageContent) {
        Message message = gson.fromJson(messageContent, Message.class);
        messages.add(message);
    }
}
