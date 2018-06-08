import client.ClientService;
import common.Message;

import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        ClientService clientService = new ClientService();

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter your username");
        clientService.username = keyboard.nextLine();
        Thread thread = new Thread(clientService);
        thread.start();

        clientService.printAllMessages();
        while(true){
            String text = keyboard.nextLine();
            Message message = new Message(text, clientService.username);
            clientService.sendMessage(message);
        }

    }
}
