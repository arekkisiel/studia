package server;

import com.google.gson.Gson;
import core.Action;
import core.IHandler;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcStreamServer;
import org.apache.xmlrpc.webserver.WebServer;

import java.io.IOException;

public class RPCServer implements IHandler{

    public String getWinner(String board) {
        if(isWinner(board,'X'))
            return "X";
        if(isWinner(board,'O'))
            return "O";
        if(!board.contains("I"))
            return "T";
        return "I";
    }

    public String updateBoard(String jsonAction) {
        Gson gson = new Gson();
        Action action = gson.fromJson(jsonAction, Action.class);
        String tmpBoard = action.getGameBoard();
        int lowBoundary = action.getFieldId()-1;
        return (tmpBoard.substring(0, lowBoundary) + action.getPlayerSign() + tmpBoard.substring(action.getFieldId()));
    }

    public Boolean isWinner(String board, char playerSign) {
        if(board.charAt(0) == playerSign && board.charAt(1) == playerSign && board.charAt(2) == playerSign)
            return true;
        if(board.charAt(3) == playerSign && board.charAt(4) == playerSign && board.charAt(5) == playerSign)
            return true;
        if(board.charAt(6) == playerSign && board.charAt(7) == playerSign && board.charAt(8) == playerSign)
            return true;
        if(board.charAt(0) == playerSign && board.charAt(3) == playerSign && board.charAt(6) == playerSign)
            return true;
        if(board.charAt(1) == playerSign && board.charAt(4) == playerSign && board.charAt(7) == playerSign)
            return true;
        if(board.charAt(2) == playerSign && board.charAt(5) == playerSign && board.charAt(8) == playerSign)
            return true;
        if(board.charAt(0) == playerSign && board.charAt(4)== playerSign && board.charAt(8) == playerSign)
            return true;
        if(board.charAt(2) == playerSign && board.charAt(4) == playerSign && board.charAt(6) == playerSign)
            return true;
        return false;
    }


    public static void main(String[] args) throws XmlRpcException, IOException {

        WebServer server = new WebServer(10000);

        XmlRpcStreamServer rpcServer = server.getXmlRpcServer();
        PropertyHandlerMapping handlers = new PropertyHandlerMapping();

//        handlers.setVoidMethodEnabled(true);

        handlers.addHandler(IHandler.class.getName(), RPCServer.class);

        rpcServer.setHandlerMapping(handlers);

        System.out.println("Startujemy serwer!");
        server.start();
    }

}