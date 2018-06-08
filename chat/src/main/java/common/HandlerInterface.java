package common;

public interface HandlerInterface {
    String fetchConversation(String username);
    boolean isNewMessage(String username);
    String fetchNewMessages(String username);
    void sendMessage(String messageContent);
}
