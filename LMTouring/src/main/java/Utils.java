import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {
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

    public static String processSeries(String series, TextFlow log) {
        List<Digit> operationalData = prepareData(series);
        int operationalIndex = 0;
        operationalIndex = findFirstNonEmptyValue(operationalIndex, operationalData);
        if (operationalIndex == 0) {
            log.getChildren().add(new Text("There are no values! \n"));
            return "3";
        }
        if (checkSign(operationalIndex, operationalData)) {
            operationalIndex = findEmptyValue(operationalIndex, operationalData);
            if (operationalIndex == 0) {
                log.getChildren().add(new Text("Something is wrong with processed number! \n"));
                return "0";
            }
            operationalIndex = shiftLeft(operationalIndex);
            log.getChildren().add(new Text("Header at index: " + operationalIndex + " Current result: " + finalConversion(operationalData) + "\n"));
            if (Integer.valueOf(operationalData.get(operationalIndex).value) >= 0 && Integer.valueOf(operationalData.get(operationalIndex).value) < 7) {
                operationalData.get(operationalIndex).plusThree();
                return finalConversion(operationalData);
            }
            if (operationalData.get(operationalIndex).value.equals("empty")) {
                operationalData.get(operationalIndex).value = "3";
                return finalConversion(operationalData);
            }
            if (Integer.valueOf(operationalData.get(operationalIndex).value) >= 7) {
                operationalData.get(operationalIndex).plusThree();
                operationalIndex = shiftLeft(operationalIndex);
                for (; operationalIndex >= 0; operationalIndex = shiftLeft(operationalIndex)) {
                    log.getChildren().add(new Text("Header at index: " + operationalIndex + " Current result: " + finalConversion(operationalData) + "\n"));
                    if (operationalData.get(operationalIndex).value.equals("empty")) {
                        operationalData.get(operationalIndex).value = "1";
                        return finalConversion(operationalData);
                    }
                    if (Integer.valueOf(operationalData.get(operationalIndex).value) >= 0 && Integer.valueOf(operationalData.get(operationalIndex).value) < 9) {
                        operationalData.get(operationalIndex).plusOne();
                        return finalConversion(operationalData);
                    }
                    operationalData.get(operationalIndex).value = "0";
                }
            }
        } else {
            operationalIndex = findEmptyValue(operationalIndex, operationalData);
            if (operationalIndex == 0) {
                log.getChildren().add(new Text("Something is wrong with processed number! \n"));
                return "0";
            }
            log.getChildren().add(new Text("Processing started...\n"));
            operationalIndex = shiftLeft(operationalIndex);
            log.getChildren().add(new Text("Header at index: " + operationalIndex + " Current result: " + finalConversion(operationalData) + "\n"));
            if (Integer.valueOf(operationalData.get(operationalIndex).value) <= 9 && Integer.valueOf(operationalData.get(operationalIndex).value) > 3) {
                operationalData.get(operationalIndex).minusThree();
                return finalConversion(operationalData);
            }
            if(operationalData.get(operationalIndex).value.equals("empty")){
                operationalData.get(operationalIndex).value = "3";
                operationalIndex = shiftLeft(operationalIndex);
                log.getChildren().add(new Text("Header at index: " + operationalIndex + " Current result: " + finalConversion(operationalData) + "\n"));
                operationalData.get(operationalIndex).value = "empty";
                return finalConversion(operationalData);
            }
            if(operationalData.get(shiftLeft(operationalIndex)).value.equals("-")){
                operationalData.get(operationalIndex).minusThreeAndSignChange();
                operationalIndex = shiftLeft(operationalIndex);
                log.getChildren().add(new Text("Header at index: " + operationalIndex + " Current result: " + finalConversion(operationalData) + "\n"));
                operationalData.get(operationalIndex).value = "empty";
                return finalConversion(operationalData);
            }
            if (Integer.valueOf(operationalData.get(operationalIndex).value) <= 3) {
                operationalData.get(operationalIndex).minusThree();
                operationalIndex = shiftLeft(operationalIndex);
                for (; operationalIndex >= 0; operationalIndex = shiftLeft(operationalIndex)) {
                    log.getChildren().add(new Text("Header at index: " + operationalIndex + " Current result: " + finalConversion(operationalData) + "\n"));
                    if (operationalData.get(operationalIndex).value.equals("-")) {
                        operationalData.get(operationalIndex).value = "empty";
                        return finalConversion(operationalData);
                    }
                    if (Integer.valueOf(operationalData.get(operationalIndex).value) > 0 && Integer.valueOf(operationalData.get(operationalIndex).value) <= 9) {
                        operationalData.get(operationalIndex).minusOne();
                        return finalConversion(operationalData);
                    }
                    operationalData.get(operationalIndex).value = "9";
                }
            }
        }
        return "Problem";
    }


    private static String finalConversion(List<Digit> operationalData) {
        List<String> finalResult = new ArrayList<>();
        for(int index = 0; index<operationalData.size();index++){
            if(!operationalData.get(index).value.equals("empty"))
                finalResult.add(operationalData.get(index).value);
        }
        return String.join("", finalResult);
    }

    private static int shiftLeft(int index) {
        return index-1;
    }

    private static int shiftRight(int index) {
        return index+1;
    }

    private static int findEmptyValue(int operationalIndex, List<Digit> operationalData) {
        for(int index=operationalIndex; index<operationalData.size(); index++){
            if(operationalData.get(index).value.equals("empty"))
                return index;
        }
        return 0;
    }

    private static int findFirstNonEmptyValue(int operationalIndex, List<Digit> operationalData) {
        for(int index=operationalIndex; index<operationalData.size(); index++){
            if(!operationalData.get(index).value.equals("empty"))
                return index;
        }
        return 0;
    }

    private static boolean checkSign(int operationalIndex, List<Digit> operationalData) {
        if(!operationalData.get(operationalIndex).value.equals("-"))
            return true;
        return false;
    }

    private static List<Digit> prepareData(String series) {
        List<Digit> data = new ArrayList<Digit>();
        data.add(0, new Digit(0, "empty"));
        for(int index=0; index<series.length();index++)
            data.add(new Digit(index+1, series.substring(index, index+1)));
        data.add(series.length()+1, new Digit(series.length()+1, "empty"));
        return data;
    }
}
