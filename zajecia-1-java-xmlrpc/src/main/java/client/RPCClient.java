package client;

import core.Action;
import core.IHandler;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.util.ClientFactory;

import com.google.gson.Gson;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Handler;

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

        playGame(handler);
    }

    private static void playGame(IHandler handler) {
        String winner = "I";
        initializeBoard();

        while(winner.contains("I")) {
            printBoard(tmpBoard);
            tmpBoard = performAction(handler, "X");
            winner = handler.getWinner(tmpBoard);
            if(winner.contains("I")) {
                printBoard(tmpBoard);
                tmpBoard = performAction(handler, "O");
                winner = handler.getWinner(tmpBoard);
            }
        }
        printBoard(tmpBoard);
        if(winner.contains("T"))
            System.out.println("Tie, no winners this time.");
        else
            System.out.println("And the winner is: PLAYER " + winner);
    }


    private static String performAction(IHandler handler, String playerSign) {
        int input;
        Gson gson = new Gson();
        int index;
        do {
            input = collectInput(playerSign);
            index = input-1;
        } while(tmpBoard.charAt(index) != 'I');

        requestData = gson.toJson(new Action(input, playerSign, tmpBoard));
        return handler.updateBoard(requestData);
    }

    private static int collectInput(String playerSign) {
        int input = 0;
        Scanner keyboard = new Scanner(System.in);

        while (input < 1 || input > 9) {
            System.out.println("Player " + playerSign + ": Enter field number (from 1 to 9):");
            input = keyboard.nextInt();
        }
        return input;
    }
}
