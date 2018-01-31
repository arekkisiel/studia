import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.Scanner;

public class Controller {
    final private char NEW_LINE = '\n';

    @FXML
    private TextArea ImportedSeries;

    @FXML
    private TextField CurrentSeries;

    @FXML TextFlow OperationalResults;

    @FXML TextFlow FinalResults;

    @FXML
    public void loadData(ActionEvent actionEvent) {
        ImportedSeries.clear();
        Scanner read = Utils.loadData();
        while (read.hasNext()) {
            ImportedSeries.appendText(read.next() + NEW_LINE);
        }
    }



    @FXML
    public void checkAllSeries(ActionEvent actionEvent) throws InterruptedException {
        String[] seriesList = ImportedSeries.getText().split("\\s");
        for (String series : seriesList) {
            if(isSeriesValid(series)) {
                String processedSeries = Utils.processSeriesStates(series, OperationalResults);
                OperationalResults.getChildren().add(new Text("Series after addition: " + processedSeries + "\n"));
                FinalResults.getChildren().add(new Text(series + " + 3 = " + processedSeries + "\n"));
            }
        }
    }

    private boolean isSeriesValid(String series) {
        try {
            Integer.parseInt(series);
            return true;
        }
        catch (NumberFormatException e){
            System.out.println("Series " + series + " is invalid and will not be processed.");
            return false;
        }
    }
}
