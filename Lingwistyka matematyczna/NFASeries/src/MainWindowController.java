import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Scanner;

public class MainWindowController {
    final private char NEW_LINE = '\n';
    private Boolean initializedSBS = false;
    private SBSAnalysisHandler analysisHandler;
    final private int[] initialStates = new int[]{0, 0};

    @FXML
    private Button LoadDataButton;

    @FXML
    private Button CheckAllSeriesButton;

    @FXML
    private TextArea ImportedSeries;

    @FXML
    private TextField CurrentSeries;

    @FXML
    private TextArea AcceptedSeries;

    @FXML
    private TextArea StateHistory;

    @FXML
    public void readAndSeparateSeries(ActionEvent actionEvent) {
        ImportedSeries.clear();
        Scanner read = Utils.loadData();
        while(read.hasNext()){
            ImportedSeries.appendText(read.next() + NEW_LINE);
        }
    }

    @FXML
    public void checkAllSeries(ActionEvent actionEvent) throws InterruptedException {
        AcceptedSeries.clear();
        String[] seriesList = ImportedSeries.getText().split("\\s");
        for (String series : seriesList) {
            CurrentSeries.setText(series);
            if (Utils.verifySeries(series, StateHistory)) {
                AcceptedSeries.appendText(series + NEW_LINE);
            }
        }
    }

    @FXML
    public void performSingleCheck(ActionEvent actionEvent) {
        if(!initializedSBS) {
            initialize();
        }
        else {
            if(isNewSeries())
                resetStateHistory();
            String series = ImportedSeries.getText().split("\\n")[analysisHandler.getSeries()];
            int word = Integer.valueOf(series.substring(analysisHandler.getWord(), analysisHandler.getWord() + 1));

            CurrentSeries.setText(series);

            for (int state : analysisHandler.getStates()) {
                if (analysisHandler.getStates()[0] != 11)
                    analysisHandler.setStates(new int[]{Utils.verifyWord(state, word), 0});
            }

            StateHistory.appendText(analysisHandler.getStates()[0] + ", " + analysisHandler.getStates()[1] + '\n');

            if (isSeriesAcceptable())
                acceptAndProcessNextSeries(series);
            else {
                if (isFinished(series)) {
                    processNextSeries();
                } else
                    processNextWord();
            }
        }
    }

    private void resetStateHistory() {
        StateHistory.clear();
    }

    public boolean isNewSeries(){
        if(analysisHandler.getWord() == 0)
            return true;
        return false;
    }

    public void processNextWord(){
        analysisHandler.setWord(analysisHandler.getWord() + 1);
    }

    public void processNextSeries(){
        int nextSeries = analysisHandler.getSeries() + 1;
        if (ImportedSeries.getText().split("\\n").length != nextSeries)
            analysisHandler.setSeries(nextSeries).setWord(0).setStates(initialStates);
        else
            initializedSBS = false;
    }

    public boolean isSeriesAcceptable(){
        if(analysisHandler.getStates()[0] == 11)
            return true;
        return false;
    }

    public boolean isFinished(String series){
        if(series.length() == (analysisHandler.getWord() + 1))
            return true;
        return false;
    }

    public void acceptAndProcessNextSeries(String series){
        AcceptedSeries.appendText(series + '\n');
        processNextSeries();
    }

    public void initialize(){
        analysisHandler = new SBSAnalysisHandler(0, 0, initialStates);
        StateHistory.clear();
        initializedSBS = true;
        String series = ImportedSeries.getText().split("\\n")[analysisHandler.getSeries()];
        CurrentSeries.setText(series);
    }
}
