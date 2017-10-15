import javafx.util.Pair;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        List<String> listOfSeries = readAndSeparateSeries();
        List<String> acceptedSeries = new ArrayList<>();
        List<Integer> stateHistory = new ArrayList<>();
        for(String series:listOfSeries){
            System.out.println(series);
            int initialState = 0;
            Boolean accepted = false;
            System.out.println("Current state: " + initialState);
            stateHistory.add(initialState);
            List<Pair<Integer, Boolean>> currentStatesList = Arrays.asList(new Pair(initialState, true));
            int state = initialState;
            for(int inputIndex=0; inputIndex < series.length(); inputIndex++){
                int word = Integer.parseInt(series.substring(inputIndex, inputIndex+1));
                for (int statesIndex = 0; statesIndex < currentStatesList.size(); statesIndex++) {
                    if(currentStatesList.get(statesIndex).getValue()) {
                        state = currentStatesList.get(statesIndex).getKey();
                        switch (state) {
                            case 0:
                                if (word != 0)
                                    currentStatesList.set(statesIndex, new Pair(word, true));
                                else
                                    currentStatesList.set(statesIndex, new Pair(10, true));
                                break;
                            case 1:
                                if (word == 1) {
                                    currentStatesList.set(statesIndex, new Pair(11, true));
                                } else
                                    currentStatesList.set(statesIndex, new Pair(1, false));
                                break;
                            case 2:
                                if (word == 2) {
                                    currentStatesList.set(statesIndex, new Pair(11, true));
                                } else
                                    currentStatesList.set(statesIndex, new Pair(2, false));
                                break;
                            case 3:
                                if (word == 3) {
                                    currentStatesList.set(statesIndex, new Pair(11, true));
                                } else
                                    currentStatesList.set(statesIndex, new Pair(3, false));
                                break;
                            case 4:
                                if (word == 4) {
                                    currentStatesList.set(statesIndex, new Pair(11, true));
                                } else
                                    currentStatesList.set(statesIndex, new Pair(4, false));
                                break;
                            case 5:
                                if (word == 5) {
                                    currentStatesList.set(statesIndex, new Pair(11, true));
                                } else
                                    currentStatesList.set(statesIndex, new Pair(5, false));
                                break;
                            case 6:
                                if (word == 6) {
                                    currentStatesList.set(statesIndex, new Pair(11, true));
                                } else
                                    currentStatesList.set(statesIndex, new Pair(6, false));
                                break;
                            case 7:
                                if (word == 7) {
                                    currentStatesList.set(statesIndex, new Pair(11, true));
                                } else
                                    currentStatesList.set(statesIndex, new Pair(7, false));
                                break;
                            case 8:
                                if (word == 8) {
                                    currentStatesList.set(statesIndex, new Pair(11, true));
                                } else
                                    currentStatesList.set(statesIndex, new Pair(8, false));
                                break;
                            case 9:
                                if (word == 9) {
                                    currentStatesList.set(statesIndex, new Pair(11, true));
                                } else
                                    currentStatesList.set(statesIndex, new Pair(9, false));
                                break;
                            case 10:
                                if (word == 0) {
                                    currentStatesList.set(statesIndex, new Pair(11, true));
                                } else
                                    currentStatesList.set(statesIndex, new Pair(10, false));
                                break;
                            case 11:
                                accepted = true;
                                break;
                        }
                    }
                    printStates(currentStatesList);
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

    private static void printStates(final List<Pair<Integer, Boolean>> currentStatesList) {
        for(int index = 0; index < currentStatesList.size(); index++){
            Pair<Integer, Boolean> pair = currentStatesList.get(index);
            if(pair.getValue())
                System.out.println(index + ". State: " + currentStatesList.get(index).getKey());
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