package core;

public class Action {
    private String gameBoard;
    private int fieldId;
    private String playerSign;

    public Action(int fieldId, String playerSign, String gameBoard){
        this.fieldId = fieldId;
        this.playerSign = playerSign;
        this.gameBoard = gameBoard;
    }

    public String getPlayerSign() {
        return playerSign;
    }

    public void setPlayerSign(String playerSign) {
        this.playerSign = playerSign;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public String getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(String gameBoard) {
        this.gameBoard = gameBoard;
    }
}
