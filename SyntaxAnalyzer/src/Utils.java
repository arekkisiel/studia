import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Utils {
    private static int INITIAL_STATE = 0;
    private static int ACCEPTED_STATE1 = 2;
    private static int ACCEPTED_STATE2 = 4;
    private static int ACCEPTED_STATE3 = 6;
    private static int ACCEPTED_STATE4 = 10;
    private static int ACCEPTED_STATE5 = 12;
    private static int INVALID_STATE = 999;
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

    public static Boolean checkGrammar(String series) {
        int currentState = INITIAL_STATE;
        for(int index = 0; index < series.length(); index++ ) {
            String currentInput = series.substring(index, index + 1);
            switch (currentState) {
                case 0:
                    if (isMinus(currentInput)) {
                        currentState = 1;
                        break;
                    }
                    if (isNumber(currentInput)) {
                        if(Integer.parseInt(currentInput) > 0) {
                            currentState = ACCEPTED_STATE1;
                            break;
                        }
                        currentState = 5;
                        break;
                    }
                    if(isOpenParenthesis(currentInput)){
                        currentState = 11;
                        break;
                    }
                    currentState = INVALID_STATE;
                    break;
                case 1:
                    if (isNumber(currentInput)) {
                        if(Integer.parseInt(currentInput) > 0) {
                            currentState = ACCEPTED_STATE1;
                            break;
                        }
                        currentState = 13;
                        break;
                    }
                    currentState = INVALID_STATE;
                    break;
                case 2:
                    if (isNumber(currentInput)) {
                        currentState = ACCEPTED_STATE1;
                        break;
                    }
                    if (isOperator(currentInput)) {
                        currentState = 3;
                        break;
                    }
                    if (isDot(currentInput)) {
                        currentState = 7;
                        break;
                    }
                    currentState = INVALID_STATE;
                    break;
                case 3:
                    if (isNumber(currentInput)) {
                        if(Integer.parseInt(currentInput) > 0) {
                            currentState = ACCEPTED_STATE2;
                            break;
                        }
                        currentState = ACCEPTED_STATE3;
                        break;
                    }
                    if(isOpenParenthesis(currentInput)){
                        currentState = 11;
                        break;
                    }
                    currentState = INVALID_STATE;
                    break;
                case 4:
                    if(isNumber(currentInput)) {
                        currentState = ACCEPTED_STATE2;
                        break;
                    }
                    if(isOperator(currentInput)) {
                        currentState = 3;
                        break;
                    }
                    if(isDot(currentInput)){
                        currentState = 9;
                        break;
                    }
                    currentState = INVALID_STATE;
                    break;
                case 5:
                    if(isOperator(currentInput)){
                        currentState = 3;
                        break;
                    }
                    if(isDot(currentInput)){
                        currentState = 7;
                        break;
                    }
                    currentState = INVALID_STATE;
                    break;
                case 6:
                    if(isOperator(currentInput)) {
                        currentState = 3;
                        break;
                    }
                    if(isDot(currentInput)){
                        currentState = 9;
                        break;
                    }
                    currentState = INVALID_STATE;
                    break;
                case 7:
                    if(isNumber(currentInput)){
                        currentState = 8;
                        break;
                    }
                    currentState = INVALID_STATE;
                    break;
                case 8:
                    if(isNumber(currentInput)){
                        break;
                    }
                    if(isOperator(currentInput)){
                        currentState = 3;
                        break;
                    }
                    currentState = INVALID_STATE;
                    break;
                case 9:
                    if(isNumber(currentInput)){
                        currentState = ACCEPTED_STATE4;
                        break;
                    }
                    currentState = INVALID_STATE;
                    break;
                case 10:
                    if(isNumber(currentInput)){
                        break;
                    }
                    if(isOperator(currentInput)){
                        currentState = 3;
                        break;
                    }
                    currentState = INVALID_STATE;
                    break;
                case 11:
                    int subseriesEndIndex = findClosingParenthesis(series.substring(index)) + index;
                    if(subseriesEndIndex > 0) {
                        if (checkGrammar(series.substring(index, subseriesEndIndex))) {
                            index += (subseriesEndIndex - 1);
                            currentState = ACCEPTED_STATE5;
                            break;
                        }
                    }
                    currentState = INVALID_STATE;
                    break;
                case 12:
                    if(isOperator(currentInput)){
                        currentState = 3;
                        break;
                    }
                    if(isOpenParenthesis(currentInput)){
                        currentState = 11;
                        break;
                    }
                    currentState = INVALID_STATE;
                    break;
                case 13:
                    if(isDot(currentInput)){
                        currentState = 7;
                        break;
                    }
                    currentState = INVALID_STATE;
                    break;
                case 999:
                    break;
            }
        }
        if(currentState == ACCEPTED_STATE1 || currentState == ACCEPTED_STATE2 || currentState == ACCEPTED_STATE3 || currentState == ACCEPTED_STATE4 || currentState == ACCEPTED_STATE5)
            return true;
        return false;
    }

    private static int findClosingParenthesis(String series) {
        if(series.indexOf("(") < series.indexOf(")")){
            int countOpen = 0;
            String tmpSeries = series;
            while(tmpSeries.contains("(")){
                countOpen++;
                int tmpIndex = tmpSeries.indexOf("(");
                tmpSeries = tmpSeries.substring(tmpIndex+1);
            }
            int finalIndex = 0;
            tmpSeries = series;
            for(int index = 0; index <= countOpen; index++){
                int tmpIndex = tmpSeries.indexOf(")");
                finalIndex += tmpIndex;
                if(tmpIndex == 0)
                    finalIndex++;
                tmpSeries = tmpSeries.substring(tmpIndex+1);
            }
            return finalIndex;
        }
        else
            return series.indexOf(")");
    }

    private static boolean isMinus(String currentInput) {
        if(currentInput.contains("-"))
            return true;
        return false;
    }

    private static boolean isOperator(String currentInput) {
        if(currentInput.contains("-") || currentInput.contains("+") || currentInput.contains("*") || currentInput.contains("/") || currentInput.contains("^"))
            return true;
        return false;
    }

    private static boolean isNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    private static boolean isDot(String currentInput) {
        if(currentInput.contains("."))
            return true;
        return false;
    }

    private static boolean isOpenParenthesis(String currentInput) {
        if(currentInput.contains("("))
            return true;
        return false;
    }
}

