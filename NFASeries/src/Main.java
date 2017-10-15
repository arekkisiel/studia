import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        List<String> listOfSeries = readAndSeparateSeries();
        List<String> acceptedSeries = new ArrayList<>();
        List<Integer> stateHistory = new ArrayList<>();
        for(String series:listOfSeries){
            System.out.println(series);
            int state = 0;
            Boolean accepted = false;
            System.out.println("Current state: " + state);
            stateHistory.add(state);
            for(int index=0; index < series.length(); index++){
                int word = Integer.parseInt(series.substring(index, index+1));
                if(!acceptedSeries.contains(series)){
                    switch (state) {
                        case 0:
                            if(word != 0)
                                state = word;
                            else
                                state = 10;
                            break;
                        case 1:
                            if(word == 1) {
                                state = 11;
                            }
                            else
                                state = word;
                            break;
                        case 2:
                            if(word == 2) {
                                state = 11;
                            }
                            else
                                state = word;
                            break;
                        case 3:
                            if(word == 3) {
                                state = 11;
                            }
                            else
                                state = word;
                            break;
                        case 4:
                            if(word == 4) {
                                state = 11;
                            }
                            else
                                state = word;
                            break;
                        case 5:
                            if(word == 5) {
                                state = 11;
                            }
                            else
                                state = word;
                            break;
                        case 6:
                            if(word == 6) {
                                state = 11;
                            }
                            else
                                state = word;
                            break;
                        case 7:
                            if(word == 7) {
                                state = 11;
                            }
                            else
                                state = word;
                            break;
                        case 8:
                            if(word == 8) {
                                state = 11;
                            }
                            else
                                state = word;
                            break;
                        case 9:
                            if(word == 9) {
                                state = 11;
                            }
                            else
                                state = word;
                            break;
                        case 10:
                            if(word == 0) {
                                state = 11;
                            }
                            else
                                state = word;
                            break;
                        case 11:
                            accepted = true;
                            break;
                    }
                    System.out.println("Current state: " + state);
                    stateHistory.add(state);
                }
            }
            if(accepted || state == 11)
                acceptedSeries.add(series);
            System.out.println("State history: " + stateHistory);
        }
        System.out.println("Accepted series:");
        for(String result:acceptedSeries) {
            System.out.println(result);
        }
    }


    public static List<String> readAndSeparateSeries(){
        Scanner read = null;
        try {
            read = new Scanner(new File("datafile.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        read.useDelimiter("#");
        List<String> listOfSeries = new ArrayList<>();
        while(read.hasNext()){
            listOfSeries.add(read.next());
        }
        return listOfSeries;
    }
}