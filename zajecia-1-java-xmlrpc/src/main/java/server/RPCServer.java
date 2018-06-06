package server;

import core.IHandler;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcHandler;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.RequestProcessorFactoryFactory;
import org.apache.xmlrpc.server.XmlRpcStreamServer;
import org.apache.xmlrpc.webserver.WebServer;

import java.io.IOException;

public class RPCServer implements IHandler{

    String board = "IIIIIIIII";

    public String getBoard() {
        return board;
    }

    public String updateBoard(String remoteBoard) {
        this.board = remoteBoard;
        return this.board;
    }

    public String whoIsWinner() {
        if(isWinner('X'))
            return "X";
        if(isWinner('O'))
            return "O";
        return "I";
    }

    public Boolean isWinner(char playerSign) {
////        horizontal
        if(board.charAt(0) == playerSign && board.charAt(1) == playerSign && board.charAt(2) == playerSign)
            return true;
        if(board.charAt(3) == playerSign && board.charAt(4) == playerSign && board.charAt(5) == playerSign)
            return true;
        if(board.charAt(6) == playerSign && board.charAt(7) == playerSign && board.charAt(8) == playerSign)
            return true;
        //vertical
        if(board.charAt(0) == playerSign && board.charAt(3) == playerSign && board.charAt(6) == playerSign)
            return true;
        if(board.charAt(1) == playerSign && board.charAt(4) == playerSign && board.charAt(7) == playerSign)
            return true;
        if(board.charAt(2) == playerSign && board.charAt(5) == playerSign && board.charAt(8) == playerSign)
            return true;
        //cross
        if(board.charAt(0) == playerSign && board.charAt(4)== playerSign && board.charAt(8) == playerSign)
            return true;
        if(board.charAt(2) == playerSign && board.charAt(4) == playerSign && board.charAt(6) == playerSign)
            return true;
        return false;
    }
//
//    public String addHello(String s) {
//        String result = "hello " + s;
//        System.out.println(String.format("%d: %s", Thread.currentThread().getId(), result));
//        return result;
//    }

//
//    public void action(int field, char playerSignature) {
//        this.board[--field] = playerSignature;
//    }

//    public boolean isFinished() {
//        if(!this.checkBoard('X'))
//            return this.checkBoard('O');
//        return true;
//    }

//    private boolean checkBoard(char sign) {
        //horizontal
//        if(this.board[0] == sign && this.board[1] == sign && this.board[2] == sign)
//            return true;
//        if(this.board[3] == sign && this.board[4] == sign && this.board[5] == sign)
//            return true;
//        if(this.board[6] == sign && this.board[7] == sign && this.board[8] == sign)
//            return true;
//        //vertical
//        if(this.board[0] == sign && this.board[3] == sign && this.board[6] == sign)
//            return true;
//        if(this.board[1] == sign && this.board[4] == sign && this.board[7] == sign)
//            return true;
//        if(this.board[2] == sign && this.board[5] == sign && this.board[8] == sign)
//            return true;
//        //cross
//        if(this.board[0] == sign && this.board[4] == sign && this.board[8] == sign)
//            return true;
//        if(this.board[2] == sign && this.board[4] == sign && this.board[6] == sign)
//            return true;
//        return false;
//    }


    public static void main(String[] args) throws XmlRpcException, IOException {

        WebServer server = new WebServer(10000);

        XmlRpcStreamServer rpcServer = server.getXmlRpcServer();
        PropertyHandlerMapping handlers = new PropertyHandlerMapping();

        handlers.setVoidMethodEnabled(true);

        handlers.addHandler(IHandler.class.getName(), RPCServer.class);

        rpcServer.setHandlerMapping(handlers);

        System.out.println("Startujemy serwer!");
        server.start();
    }

}