public class Game implements GameInterface {

    PlayerO player1 = new PlayerO();
    PlayerX player2 = new PlayerX();;
    char[] board =  new char[] {'I', 'I', 'I', 'I', 'I', 'I', 'I', 'I', 'I'};

    public void play() {
        this.printBoard();
        this.board = player1.move(this.board);
        this.printBoard();
        if(!this.isFinished())
            this.board = player2.move(this.board);
    }

    public void restart() {
        this.board = new char[]{'I', 'I', 'I', 'I', 'I', 'I', 'I', 'I', 'I'};
    }

    public boolean isFinished() {
        if(!this.checkBoard(this.player1.playerSignature))
            return this.checkBoard(this.player2.playerSignature);
        return true;
    }

    private boolean checkBoard(char sign) {
        //horizontal
        if(this.board[0] == sign && this.board[1] == sign && this.board[2] == sign)
            return true;
        if(this.board[3] == sign && this.board[4] == sign && this.board[5] == sign)
            return true;
        if(this.board[6] == sign && this.board[7] == sign && this.board[8] == sign)
            return true;
        //vertical
        if(this.board[0] == sign && this.board[3] == sign && this.board[6] == sign)
            return true;
        if(this.board[1] == sign && this.board[4] == sign && this.board[7] == sign)
            return true;
        if(this.board[2] == sign && this.board[5] == sign && this.board[8] == sign)
            return true;
        //cross
        if(this.board[0] == sign && this.board[4] == sign && this.board[8] == sign)
            return true;
        if(this.board[2] == sign && this.board[4] == sign && this.board[6] == sign)
            return true;
        return false;
    }


    public void printBoard() {
        System.out.println("Current game board:");
        System.out.println("" + this.board[6] + this.board[7] + this.board[8]);
        System.out.println("" + this.board[3] + this.board[4] + this.board[5]);
        System.out.println("" + this.board[0] + this.board[1] + this.board[2]);
    }
}
