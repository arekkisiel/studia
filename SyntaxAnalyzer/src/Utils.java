import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Utils {
    private static String PRODUCTION;
    private static String INPUT;
    /*PRODUCTIONS
    S = AFAE | (S)E
    A = CB | (-CB)
    B = ,C |  $
    C = 0D | GD
    D = 0D | GD | $
    E = FS | $
    F = +|-|*|/|^
    G = 1|2|3|4|5|6|7|8|9
    */

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


    public static boolean checkGrammar(String input){
        int iterator = 0;
        //initialization
        INPUT = input;
        PRODUCTION = "S";
        while(PRODUCTION.length() > 0){
            if(INPUT.length() == 0)
                INPUT = "$";
            String partial_production = (fitProduction(first(INPUT), first(PRODUCTION)));
            if(partial_production.equals("INVALID"))
                return false;
            PRODUCTION = partial_production.concat(PRODUCTION.substring(1));
            Boolean dropped;
            do {
                dropped = dropIfEqual();
            }while(dropped == true && PRODUCTION.length() > 0 && INPUT.length() > 0);
        }
        if(PRODUCTION.length() == 0 && INPUT.length() > 0)
            return false;
        return true;
    }

    private static boolean dropIfEqual() {
        if(first(INPUT).equals(first(PRODUCTION))){
            INPUT = INPUT.substring(1);
            PRODUCTION = PRODUCTION.substring(1);
            return true;
        }
        else if(first(PRODUCTION).equals("$")) {
            PRODUCTION = PRODUCTION.substring(1);
            return true;
        }
        return false;
    }

    private static String fitProduction(String input, String production){
        switch (production){
    /*PRODUCTIONS
    S=AEJ|(S)J
    A=CB|(K |-CB
    B=.I|$
    C=0|GD
    D=0D|GD|$
    E= FLE|$
    F = +|-|*|/|^
    G = 1|2|3|4|5|6|7|8|9
    H=0H|GH|$
    I=0H|GH
    J=FS|$
    K=-CB)|S)
    L=CB|(K
    */
            case "S":
                if(input.contains("("))
                    return "(S)J";
                return "AEJ";
            case "A":
                if(input.contains("("))
                    return "(K";
                if(input.contains("-"))
                    return "-CB";
                return "CB";
            case "B":
                if(input.contains("."))
                    return ".I";
                return "$";
            case "C":
                if(input.contains("0"))
                    return "0";
                return "GD";
            case "D":
                if(input.contains("0"))
                    return "0D";
                if(isDigit(input))
                    return "GD";
                return "$";
            case "E":
                if(isOperator(input))
                    return "FLE";
                return "$";
            case "F":
                if(input.contains("+"))
                    return "+";
                if(input.contains("-"))
                    return "-";
                if(input.contains("*"))
                    return "*";
                if(input.contains("/"))
                    return "/";
                if(input.contains("^"))
                    return "^";
                break;
            case "G":
                if(input.contains("1"))
                    return "1";
                if(input.contains("2"))
                    return "2";
                if(input.contains("3"))
                    return "3";
                if(input.contains("4"))
                    return "4";
                if(input.contains("5"))
                    return "5";
                if(input.contains("6"))
                    return "6";
                if(input.contains("7"))
                    return "7";
                if(input.contains("8"))
                    return "8";
                if(input.contains("9"))
                    return "9";
                break;
            case "H":
                if(input.contains("0"))
                    return "0H";
                if(isDigit(input))
                    return "GH";
                return "$";
            case "I":
                if(input.contains("0"))
                    return "0H";
                if(isDigit(input))
                    return "GH";
                break;
            case "J":
                if(isOperator(input))
                    return "FS";
                return "$";
            case "K":
                if(input.contains("-"))
                    return "-CB)";
                return "S)";
            case "L":
                if(input.contains("("))
                    return "(K";
                return "CB";
        }
        //Throw ERROR, INPUT does not fit to the GRAMMAR
        return "INVALID";
    }

    private static String first(String input) {
        return input.substring(0, 1);
    }


    //CHECK_TERMINALS
    private static boolean isZero (String input) {
        if(input.contains("0"))
            return true;
        return false;
    }

    private static boolean isDigit (String input) {
        List<String> digits = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");
        if(digits.contains(input))
            return true;
        return false;
    }

    private static boolean isOperator (String input) {
        List<String> operators = Arrays.asList("-", "+", "*", "/", "^");
        if(operators.contains(input))
            return true;
        return false;
    }


}

