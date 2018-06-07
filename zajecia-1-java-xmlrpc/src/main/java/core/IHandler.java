package core;

public interface IHandler {

//    String addHello(String s);
//    void playerMove(int fieldId, char playerSignature);
//    boolean isFinished();
//    void printBoard();
    String getWinner(String board);
//    void initializeNewBoard();
    String updateBoard(String jsonAction);
}