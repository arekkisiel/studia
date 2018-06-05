import java.util.Scanner;

public class PlayerO implements PlayerInterface {

    char playerSignature;

    PlayerO(){
        this.playerSignature = 'O';
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
        board[--field] = 'O';
        return board;
    }
}
