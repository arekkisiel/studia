import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.Scanner;

public class Controller {
    final private char NEW_LINE = '\n';

    @FXML
    private TextArea ImportedSeries;

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
    public void checkAllSeries(ActionEvent actionEvent) {
        String[] seriesList = ImportedSeries.getText().split("\\s");
        for (String series : seriesList) {
            Boolean isValid = Utils.checkGrammar(series);
            if(isValid)
                FinalResults.getChildren().add(new Text(series + " is correct." + "\n"));
            else
                FinalResults.getChildren().add(new Text(series + " is incorrect." + "\n"));
        }
    }
}
