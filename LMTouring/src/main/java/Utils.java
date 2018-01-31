import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

public class Utils {
    private static int INITIAL_STATE = 0;
    private static int FINAL_STATE = 8;
    public static Scanner loadData() {
        Scanner read = null;
        try {
            read = new Scanner(new File("datafile.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        read.useDelimiter("#");
        return read;
    }

    private static String finalConversion(List<Digit> operationalData) {
        List<String> finalResult = new ArrayList<>();
        for(int index = 0; index<operationalData.size();index++){
            if(!operationalData.get(index).value.equals("empty"))
                finalResult.add(operationalData.get(index).value);
        }
        return String.join("", finalResult);
    }

    private static String tmpConversion(List<Digit> operationalData) {
        List<String> finalResult = new ArrayList<>();
        for(int index = 0; index<operationalData.size();index++){
            if(!operationalData.get(index).value.equals("empty"))
                finalResult.add(operationalData.get(index).value);
            else
                finalResult.add("∅");
        }
        return String.join("", finalResult);
    }

    public static String processSeriesStates(String series, TextFlow log) {
        int state = INITIAL_STATE;
        List<Digit> operationalData = prepareData(series);
        int operationalIndex = 0;
        while(state != FINAL_STATE) {
            printData(state, operationalIndex, operationalData, log);
            Digit currentInput = operationalData.get(operationalIndex);
            switch (state) {
                case 0:
                    if (currentInput.value.contains("empty")) {
                        state = 0;
                        operationalIndex = shiftRight(operationalIndex);
                        break;
                    }
                    if (currentInput.value.contains("-")) {
                        state = 4;
                        operationalIndex = shiftRight(operationalIndex);
                        break;
                    }
                    if (Integer.valueOf(currentInput.value) >= 0 && Integer.valueOf(currentInput.value) <= 9) {
                        state = 1;
                        operationalIndex = shiftRight(operationalIndex);
                        break;
                    }
                case 1:
                    if (currentInput.value.contains("empty")) {
                        state = 2;
                        operationalIndex = shiftLeft(operationalIndex);
                        break;
                    }
                    if (Integer.valueOf(currentInput.value) >= 0 && Integer.valueOf(currentInput.value) <= 9) {
                        state = 1;
                        operationalIndex = shiftRight(operationalIndex);
                        break;
                    }
                case 2:
                    if (currentInput.value.contains("empty")) {
                        state = FINAL_STATE;
                        operationalData.get(operationalIndex).value = String.valueOf(3);
                        break;
                    }
                    if (Integer.valueOf(currentInput.value) >= 0 && Integer.valueOf(currentInput.value) <= 6) {
                        state = FINAL_STATE;
                        operationalData.get(operationalIndex).plusThree();
                        break;
                    }
                    if (Integer.valueOf(currentInput.value) >= 7 && Integer.valueOf(currentInput.value) <= 9) {
                        state = 3;
                        operationalData.get(operationalIndex).plusThree();
                        operationalIndex = shiftLeft(operationalIndex);
                        break;
                    }
                case 3:
                    if (currentInput.value.contains("empty")) {
                        state = FINAL_STATE;
                        operationalData.get(operationalIndex).value = String.valueOf(1);
                        break;
                    }
                    if (Integer.valueOf(currentInput.value) >= 0 && Integer.valueOf(currentInput.value) <= 8) {
                        state = FINAL_STATE;
                        operationalData.get(operationalIndex).plusOne();
                        break;
                    }
                    if (Integer.valueOf(currentInput.value) == 9) {
                        state = 3;
                        operationalData.get(operationalIndex).value = String.valueOf(0);
                        operationalIndex = shiftLeft(operationalIndex);
                        break;
                    }
                case 4:
                    if (currentInput.value.contains("empty")) {
                        state = 5;
                        operationalIndex = shiftLeft(operationalIndex);
                        break;
                    }
                    if (Integer.valueOf(currentInput.value) >= 0 && Integer.valueOf(currentInput.value) <= 9) {
                        state = 4;
                        operationalIndex = shiftRight(operationalIndex);
                        break;
                    }
                case 5:
                    if (currentInput.value.contains("empty")) {
                        state = 9;
                        operationalData.get(operationalIndex).value = String.valueOf(3);
                        operationalIndex = shiftLeft(operationalIndex);
                        break;
                    }
                    if (Integer.valueOf(currentInput.value) >= 0 && Integer.valueOf(currentInput.value) <= 2) {
                        state = 6;
                        operationalIndex = shiftLeft(operationalIndex);
                        break;
                    }
                    if (Integer.valueOf(currentInput.value) >= 3 && Integer.valueOf(currentInput.value) <= 9) {
                        state = FINAL_STATE;
                        operationalData.get(operationalIndex).minusThree();
                        break;
                    }
                case 6:
                    if (currentInput.value.contains("-")) {
                        state = 10;
                        operationalData.get(operationalIndex).value = "empty";
                        operationalIndex = shiftRight(operationalIndex);
                        break;
                    }
                    if (Integer.valueOf(currentInput.value) >= 0 && Integer.valueOf(currentInput.value) <= 2) {
                        state = 11;
                        operationalIndex = shiftRight(operationalIndex);
                        break;
                    }
                case 7:
                    if (Integer.valueOf(currentInput.value) == 0) {
                        state = 7;
                        operationalData.get(operationalIndex).minusOneAndSignChange();
                        operationalIndex = shiftLeft(operationalIndex);
                        break;
                    }
                    if (Integer.valueOf(currentInput.value) >= 2 && Integer.valueOf(currentInput.value) <= 9) {
                        state = FINAL_STATE;
                        operationalData.get(operationalIndex).minusOne();
                        break;
                    }
                    if (Integer.valueOf(currentInput.value) == 1) {
                        state = FINAL_STATE;
                        operationalData.get(operationalIndex).value = "empty";
                        break;
                    }
                case 8: //final
                    state = FINAL_STATE;
                    break;
                case 9:
                    state = FINAL_STATE;
                    operationalData.get(operationalIndex).value = "empty";
                    break;
                case 10:
                    if (Integer.valueOf(currentInput.value) >= 0 && Integer.valueOf(currentInput.value) <= 9) {
                        state = FINAL_STATE;
                        operationalData.get(operationalIndex).minusThreeAndSignChange();
                    }
                    break;
                case 11:
                    if (Integer.valueOf(currentInput.value) >= 0 && Integer.valueOf(currentInput.value) <= 9) {
                        state = 7;
                        operationalData.get(operationalIndex).minusThree();
                        operationalIndex = shiftLeft(operationalIndex);
                    }
                    break;
            }
        }
        printData(state, operationalIndex, operationalData, log);
        return finalConversion(operationalData);
    }

    private static int shiftLeft(int index) {
        return index-1;
    }

    private static int shiftRight(int index) {
        return index+1;
    }

    private static List<Digit> prepareData(String series) {
        List<Digit> data = new ArrayList<Digit>();
        data.add(0, new Digit(0, "empty"));
        for(int index=0; index<series.length();index++)
            data.add(new Digit(index+1, series.substring(index, index+1)));
        data.add(series.length()+1, new Digit(series.length()+1, "empty"));
        return data;
    }

    private static void printData(int state, int operationalIndex, List<Digit> tape, TextFlow log){
        log.getChildren().add(new Text("State: " + state + " Header: " + operationalIndex + " Tape: " + tmpConversion(tape) + "\n"));
    }
}
