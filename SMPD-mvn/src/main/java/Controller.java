import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    List<Entry> data = new ArrayList<>();

    @FXML
    private Button LoadDataButton;

    @FXML
    private TextArea ImportedData;

    @FXML
    private TextFlow DataStats;

    @FXML
    private TextArea SetSize;

    @FXML
    private TextArea SetSize2;

    @FXML
    public void loadData(ActionEvent actionEvent) {
        ImportedData.clear();
        data = Utils.loadData();
        for (Entry object : data){
            ImportedData.appendText(object.classType + '\n');
        }
        Text text1 = new Text("Acer objects: " + Utils.countClassObjects(data, "Acer") + '\n');
        Text text2 = new Text("Quercus objects: " + Utils.countClassObjects(data, "Quercus") + '\n');
        Text text3 = new Text("Total objects: " + data.size() + '\n');
        DataStats.getChildren().add(text1);
        DataStats.getChildren().add(text2);
        DataStats.getChildren().add(text3);
    }

    @FXML
    public void selectBestSingleFeature(ActionEvent actionEvent) {
        double max = 0;
        int selectedFeatureId = 0;
        for(int counter = 0; counter < data.get(0).features.size(); counter++){
            double result = MathOperations.calculateFisher(data, counter);
            if(result>max){
                max = result;
                selectedFeatureId = counter;
            }
            DataStats.getChildren().add(new Text("Fisher for feature " + counter + ": " + result + '\n'));
        }
        DataStats.getChildren().add(new Text("Selected feature id: " + selectedFeatureId + '\n'));
    }

    @FXML
    public void selectBestSetOfFeatures(ActionEvent actionEvent) {
        if(Integer.parseInt(SetSize.getText()) == 1){
            selectBestSingleFeature(actionEvent);
        }
        else {
            List<List<Integer>> possibleCombinations = MathOperations.defineCombinations(data.get(0).features.size(), Integer.parseInt(SetSize.getText()));
            double max = 0;
            List<Integer> selectedCombination = new ArrayList<>();
            for (List<Integer> combination : possibleCombinations) {
                double result = MathOperations.calculateFisher(data, combination);
                if (result > max) {
                    max = result;
                    selectedCombination = combination;
                }
                DataStats.getChildren().add(new Text("Fisher for combination " + combination + ": " + result + '\n'));
            }
            DataStats.getChildren().add(new Text("Selected combination: " + selectedCombination + '\n'));
        }
    }

    @FXML
    public void sfsAlgorithm(ActionEvent actionEvent) {

        int expectedSize = Integer.parseInt(SetSize2.getText());
        if(expectedSize==1){
            selectBestSingleFeature(actionEvent);
        }
        else {
            double max = 0;
            List<List<Integer>> possibleCombinations = MathOperations.defineCombinations(data.get(0).features.size(), 2);
            List<Integer> selectedCombination = new ArrayList<>();
            for (List<Integer> combination : possibleCombinations) {
                double result = MathOperations.calculateFisher(data, combination);
                if (result > max) {
                    max = result;
                    selectedCombination = combination;
                }
            }
            for (int iteration = 2; iteration < expectedSize; iteration++) {
                int selectedCounter = 0;
                max = 0;
                for (int counter = 0; counter < data.get(0).features.size(); counter++) {
                    List<Integer> tmpSet = new ArrayList<>();
                    tmpSet.addAll(selectedCombination);
                    tmpSet.add(counter);
                    double result = MathOperations.calculateFisher(data, tmpSet);
                    if (result > max && !selectedCombination.contains(counter)) {
                        max = result;
                        selectedCounter = counter;
                    }
                }
                selectedCombination.add(selectedCounter);
            }
            DataStats.getChildren().add(new Text("[SFS} Selected features: " + selectedCombination.toString() + '\n'));
        }
    }

    @FXML
    public void verifyInput(KeyEvent keyEvent) {
        try {
            int input = Integer.parseInt(SetSize.getText());
            if(!(input > 0 && input < 65)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Invalid input");
                alert.setContentText("Please insert only numbers from 1 to 64!");
                alert.showAndWait();
                SetSize.clear();
            }
        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please insert only numbers!");
            alert.showAndWait();
            SetSize.clear();
        }
    }
}
