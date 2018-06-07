package client;

import core.Action;
import core.IHandler;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.util.ClientFactory;

import com.google.gson.Gson;
import java.net.URL;
import java.util.Scanner;

public class RPCClient {

    private static String tmpBoard;
    private static String requestData;

    public static void printBoard(String board) {
        System.out.println("Current game board:");
        System.out.println("" + board.substring(6,9));
        System.out.println("" + board.substring(3,6));
        System.out.println("" + board.substring(0,3));
    }

    public static void initializeBoard() {
        tmpBoard = "IIIIIIIII";
    }

    public static void main(String[] args) throws Throwable {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

        config.setServerURL(new URL("http://localhost:10000"));
        config.setEnabledForExtensions(true);
        config.setConnectionTimeout(1000);

        XmlRpcClient client = new XmlRpcClient();

        client.setConfig(config);

        ClientFactory factory = new ClientFactory(client);

        IHandler handler = (IHandler)factory.newInstance(IHandler.class);

        Gson gson = new Gson();
        Scanner keyboard = new Scanner(System.in);
        int input;
        String winner = "I";

        initializeBoard();
        while(winner.contains("I")) {
            printBoard(tmpBoard);
            input = 0;
            while (input < 1 || input > 9) {
                System.out.println("Player X: Enter field number (from 1 to 9):");
                input = keyboard.nextInt();
            }
            requestData = gson.toJson(new Action(input, "X", tmpBoard));
            tmpBoard = handler.updateBoard(requestData);
            winner = handler.getWinner(tmpBoard);
            if(winner.contains("I")) {
                printBoard(tmpBoard);
                input = 0;
                while (input < 1 || input > 9) {
                    System.out.println("Player O: Enter field number (from 1 to 9):");
                    input = keyboard.nextInt();
                }
                requestData = gson.toJson(new Action(input, "O", tmpBoard));
                tmpBoard = handler.updateBoard(requestData);
                winner = handler.getWinner(tmpBoard);
            }
        }
    }
}
