package client;

import com.caucho.hessian.client.HessianProxyFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import common.HandlerInterface;
import common.Message;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientService implements Runnable {

    public String username = null;

    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    private HandlerInterface chatHandlerInterface;
    private Gson gson = new Gson();

    public ClientService() {
        HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
        try {
            chatHandlerInterface = (HandlerInterface)hessianProxyFactory.create(HandlerInterface.class, "http://localhost:10000/chat");
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        while (true){
            if(username != null){
                if(chatHandlerInterface.isNewMessage(this.username)) {
                    String messages = chatHandlerInterface.fetchNewMessages(this.username);
                    this.printMessages(messages);
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void sendMessage(Message message){
        String chatMessageContent = gson.toJson(message);
        //Action executed in thread pool
        executorService.execute(()->{
            chatHandlerInterface.sendMessage(chatMessageContent);
        });
    }

    public void printAllMessages(){
        printMessages(chatHandlerInterface.fetchConversation(this.username));
    }

    private void printMessages(String messages){
        Type listType = new TypeToken<ArrayList<Message>>() {}.getType();
        ArrayList<Message> chatMessages = gson.fromJson(messages, listType);
        for (Message message : chatMessages) {
            System.out.println("User: " + message.getUsername() + ": " + message.getMessage());
        }
    }

}
