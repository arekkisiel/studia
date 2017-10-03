import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Parkometer {
    final List acceptableCoins = Arrays.asList(1,2,5);
    List<Integer> insertedCoins = new ArrayList<>();
    List states = new ArrayList(Arrays.asList());
    int state = 0;

    int insertCoin(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert coin:");
        int coinValue = scanner.nextInt();
        if(acceptableCoins.contains(coinValue)){
            return coinValue;
        }
        System.out.println("Inserted coin was not accepted.");
        return 0;
    }

    void sellTicket(){
        printTicket();
        this.insertedCoins.clear();
    }

    private void printTicket(){
        System.out.println("Ticket printed.");
        System.out.println("State history: " + this.states);
    }

    protected void returnMoney(){
        System.out.println("State history: " + this.states);
        System.out.println("You have inserted too much, all money was returned.");
        this.insertedCoins.clear();
    }

    public Parkometer(){
    }
}
