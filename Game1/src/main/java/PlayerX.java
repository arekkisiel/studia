import java.util.Scanner;

public class PlayerX implements PlayerInterface {

    char playerSignature;

    PlayerX(){
        this.playerSignature = 'X';
    }

    public char[] move(char[] board){
        Scanner keyboard = new Scanner(System.in);
        int input = 0;
        while(input < 1 || input > 9) {
            System.out.println("Player " + this.playerSignature + ": Enter field number (from 1 to 9):");
            input = keyboard.nextInt();
        }
        return this.action(input, board);
    }

    public char[] action(int field, char[] board) {
        board[--field] = this.playerSignature;
        return board;
    }
}
