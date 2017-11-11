import javafx.scene.control.TextArea;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Utils
{
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

    public static Boolean verifySeries(String series, TextArea stateHistory) throws InterruptedException {
        int[] currentStatesList = new int[] {0,0};
        stateHistory.setText("0\n");

        for(int inputIndex=0; inputIndex < series.length(); inputIndex++){
            int word = Integer.parseInt(series.substring(inputIndex, inputIndex+1));
            for (int statesIndex = 0; statesIndex < 2; statesIndex++) {
                currentStatesList[0] = verifyWord(currentStatesList[statesIndex], word);
                if(currentStatesList[0] == 11){
                    stateHistory.appendText(currentStatesList[0] + ", " + currentStatesList[1] + '\n');
                    return true;
                }
            }
            stateHistory.appendText(currentStatesList[0] + ", " + currentStatesList[1] + '\n');
//            TimeUnit.SECONDS.sleep(1);
        }
        return false;
    }


    public static int verifyWord(int state, int word){
        switch (state) {
            case 0:
                if (word != 0)
                    return word;
                else
                    return 10;
            case 1:
                if (word == 1) {
                    return 11;
                } else
                    return 1;
            case 2:
                if (word == 2) {
                    return 11;
                } else
                    return 2;
            case 3:
                if (word == 3) {
                    return 11;
                } else
                    return 3;
            case 4:
                if (word == 4) {
                    return 11;
                } else
                    return 4;
            case 5:
                if (word == 5) {
                    return 11;
                } else
                    return 5;
            case 6:
                if (word == 6) {
                    return 11;
                } else
                    return 6;
            case 7:
                if (word == 7) {
                    return 11;
                } else
                    return 7;
            case 8:
                if (word == 8) {
                    return 11;
                } else
                    return 8;
            case 9:
                if (word == 9) {
                    return 11;
                } else
                    return 9;
            case 10:
                if (word == 0) {
                    return 11;
                } else
                    return 10;
        }
        return state;
    }
}
