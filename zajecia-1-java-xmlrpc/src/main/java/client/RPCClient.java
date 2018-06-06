package client;

import core.IHandler;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.client.AsyncCallback;
import org.apache.xmlrpc.client.TimingOutCallback;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.util.ClientFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

public class RPCClient {

    private static String tmpBoard;
//    private static String tmpBoard;
    public static void printBoard(String board) {
        System.out.println("Current game board:");
        System.out.println("" + board.substring(6,9));
        System.out.println("" + board.substring(3,6));
        System.out.println("" + board.substring(0,3));
//        System.out.println("" + tmpBoard[6] + tmpBoard[7] + tmpBoard[8]);
//        System.out.println("" + tmpBoard[3] + tmpBoard[4] + tmpBoard[5]);
//        System.out.println("" + tmpBoard[0] + tmpBoard[1] + tmpBoard[2]);
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
        tmpBoard = handler.updateBoard("IIIIIIIII");
        Scanner keyboard = new Scanner(System.in);
        int input;
        String winner = "I";
        while(winner.contains("I")) {
            printBoard(handler.getBoard());
            input = 0;
            while (input < 1 || input > 9) {
                System.out.println("Player X: Enter field number (from 1 to 9):");
                input = keyboard.nextInt();
            }
            int low = input-1;
            tmpBoard = tmpBoard.substring(0, low) + 'X' + tmpBoard.substring(input);
            handler.updateBoard(tmpBoard);
            winner = handler.whoIsWinner();
            if(winner.contains("I")) {
                printBoard(handler.getBoard());
                input = 0;
                while (input < 1 || input > 9) {
                    System.out.println("Player O: Enter field number (from 1 to 9):");
                    input = keyboard.nextInt();
                }
                low = input-1;
                tmpBoard = tmpBoard.substring(0, low) + 'O' + tmpBoard.substring(input);
                handler.updateBoard(tmpBoard);
                winner = handler.whoIsWinner();
            }
        }
//            handler.updateBoard(tmpBoard);
//            printBoard();
//            if(true){
//                input = 0;
//                while(input < 1 || input > 9) {
//                    System.out.println("Player O: Enter field number (from 1 to 9):");
//                    input = keyboard.nextInt();
//                }
//                tmpBoard[--input] = 'O';
//                handler.updateBoard(tmpBoard);
//            }
//        }
    }
}
