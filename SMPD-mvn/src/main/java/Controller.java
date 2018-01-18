import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Controller {
    List<Entry> data = new ArrayList<>();
    List<Entry> trainingSet = new ArrayList<>();
    List<Entry> testingSet = new ArrayList<>();
    List<Integer> sfsResults = new ArrayList<>();

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
    private TextArea SetSize3;

    @FXML
    private TextArea K;

    @FXML
    private TextArea GroupAmount;

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
    public int selectBestSingleFeature(ActionEvent actionEvent) {
        double max = 0;
        int selectedFeatureId = 0;
        for(int counter = 0; counter < data.get(0).features.size(); counter++){
            double result = MathOperations.calculateFisher(data, counter);
            if(result>max){
                max = result;
                selectedFeatureId = counter;
            }
        }
        DataStats.getChildren().add(new Text("Selected feature id: " + selectedFeatureId + '\n'));
        return selectedFeatureId;
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
            }
            DataStats.getChildren().add(new Text("Selected combination: " + selectedCombination + '\n'));
        }
    }

    @FXML
    public void sfsAlgorithm(ActionEvent actionEvent) {
        sfsResults.clear();
        int expectedSize = Integer.parseInt(SetSize2.getText());
        if(expectedSize==1){
            sfsResults.add(selectBestSingleFeature(actionEvent));
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
            DataStats.getChildren().add(new Text("[SFS] Selected features: " + selectedCombination.toString() + '\n'));
            sfsResults.addAll(selectedCombination);
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

    @FXML
    public void verifyInputK(KeyEvent keyEvent) {
        try {
            int input = Integer.parseInt(K.getText());
            if(!(input > 0 && input < 65)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Invalid input");
                alert.setContentText("Please insert only numbers from 1 to 64!");
                alert.showAndWait();
                K.clear();
            }
        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please insert only numbers!");
            alert.showAndWait();
            K.clear();
        }
    }

    @FXML
    public void verifyInputSFS(KeyEvent keyEvent) {
        try {
            int input = Integer.parseInt(SetSize2.getText());
            if(!(input > 0 && input < 65)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Invalid input");
                alert.setContentText("Please insert only numbers from 1 to 64!");
                alert.showAndWait();
                SetSize2.clear();
            }
        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please insert only numbers!");
            alert.showAndWait();
            SetSize2.clear();
        }
    }

    @FXML
    public void verifyInputTrainingSetSize(KeyEvent keyEvent) {
        try {
            int input = Integer.parseInt(SetSize3.getText());
            if(!(input > 0 && input <= data.size())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Invalid input");
                alert.setContentText("Please insert only numbers from range of available entries!");
                alert.showAndWait();
                SetSize3.clear();
            }
        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please insert only numbers!");
            alert.showAndWait();
            SetSize3.clear();
        }
    }

    @FXML
    public void verifyInputGroups(KeyEvent keyEvent) {
        try {
            int input = Integer.parseInt(GroupAmount.getText());
            if(!(input > 0 && input <= data.size())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Invalid input");
                alert.setContentText("Please insert only numbers from range of available entries!");
                alert.showAndWait();
                GroupAmount.clear();
            }
        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please insert only numbers!");
            alert.showAndWait();
            GroupAmount.clear();
        }
    }

    @FXML
    public void train(ActionEvent actionEvent) {
        trainingSet.clear();
        testingSet.clear();
        int trainingSetSize = Integer.parseInt(SetSize3.getText());
        for(int index=0; index<trainingSetSize; index++){
            trainingSet.add(data.get(index));
        }
        for(int index=trainingSetSize; index<data.size(); index++){
            testingSet.add(data.get(index));
        }
        DataStats.getChildren().add(new Text("Training set size: " + trainingSet.size() + '\n'));
        DataStats.getChildren().add(new Text("Testing set size: " + testingSet.size() + '\n'));
    }

    private List<Entry> selectQuercus() {
        List<Entry> quercusData = new ArrayList<>();
        for(Entry entry : data){
            if(entry.classType.contains("Quercus")){
                quercusData.add(entry);
            }
        }
        return quercusData;
    }

    private List<Entry> selectAcer() {
        List<Entry> acerData = new ArrayList<>();
        for(Entry entry : data){
            if(entry.classType.contains("Acer")){
                acerData.add(entry);
            }
        }
        return acerData;
    }

    @FXML
    public double NNclassification(ActionEvent actionEvent) {
        double tempDistance;
        int successfulGuess = 0;
        int totalProbes = testingSet.size();
        for(int iterator = 0; iterator < totalProbes; iterator++){
            double shortestDistance = Utils.calculateDistance(testingSet.get(iterator), trainingSet.get(0), sfsResults);
            String assignedClassType = trainingSet.get(0).classType;
            for(int secondIterator = 0; secondIterator < trainingSet.size(); secondIterator++){
                tempDistance = Utils.calculateDistance(testingSet.get(iterator), trainingSet.get(secondIterator), sfsResults);
                if(tempDistance < shortestDistance){
                    shortestDistance = tempDistance;
                    assignedClassType = trainingSet.get(secondIterator).classType;
                }
            }
            if(assignedClassType.contains("Quercus")){
                if(testingSet.get(iterator).classType.contains("Quercus"))
                    successfulGuess++;
            }
            else {
                if(testingSet.get(iterator).classType.contains("Acer"))
                    successfulGuess++;
            }
        }
        double percentage = 100*((double)successfulGuess/totalProbes);
        DataStats.getChildren().add(new Text("Successful guesses: " + successfulGuess + " out of " + testingSet.size() + '\n'));
        DataStats.getChildren().add(new Text("NN classificator quality: " + percentage + "%" + '\n'));
        return percentage;
    }

    @FXML
    public double kNNclassification(ActionEvent actionEvent) {
        int successfulGuess = 0;
        int totalProbes = testingSet.size();
        for(int iterator = 0; iterator < totalProbes; iterator++){
            Pair distanceTable[] = new Pair[trainingSet.size()];
            for(int secondIterator = 0; secondIterator < trainingSet.size(); secondIterator++){
                distanceTable[secondIterator] = new Pair<>(trainingSet.get(secondIterator).classType,Utils.calculateDistance(testingSet.get(iterator), trainingSet.get(secondIterator), sfsResults));
            }
            //Pair sortedDistanceTable [] = Utils.bubbleSort(distanceTable);
            //DataStats.getChildren().add(new Text("Probe number " + iterator + " was classified as " + assignedClassType + " and correct result is " + testingSet.get(iterator).classType + '\n'));
            int k = Integer.parseInt(K.getText());
            Pair closestNeighbours[] = new Pair[k];
            for(int index=0; index < k; index++)
                closestNeighbours[index] = new Pair("Temp", 1000);
            for(Pair distance : distanceTable) {
                boolean inserted = false;
                for (int index = 0; index < k; index++){
                    if(!inserted) {
                        if (Double.parseDouble(distance.getValue().toString()) < Double.parseDouble(closestNeighbours[index].getValue().toString())) {
                            for (int secondaryIndex = k - 1; secondaryIndex > 1; secondaryIndex--) {
                                closestNeighbours[secondaryIndex - 1] = closestNeighbours[secondaryIndex - 2];
                            }
                            closestNeighbours[index] = distance;
                            inserted = true;
                        }
                    }
                }
            }
            int acerGuess = 0;
            int quercusGuess = 0;
            for(Pair entry : closestNeighbours){
                if(entry.getKey().toString().contains("Acer"))
                        acerGuess++;
                if(entry.getKey().toString().contains("Quercus"))
                        quercusGuess++;
            }
            String assignedClassType;
            if(acerGuess > quercusGuess)
                assignedClassType = "Acer";
            else
                assignedClassType = "Quercus";
            if(assignedClassType.contains("Quercus")){
                if(testingSet.get(iterator).classType.contains("Quercus"))
                    successfulGuess++;
            }
            else {
                if(testingSet.get(iterator).classType.contains("Acer"))
                    successfulGuess++;
            }
        }
        double percentage = 100*((double)successfulGuess/totalProbes);
        DataStats.getChildren().add(new Text("Successful guesses: " + successfulGuess + " out of " + testingSet.size() + '\n'));
        DataStats.getChildren().add(new Text("k-NN classificator quality: " + percentage + "%" + '\n'));
        return percentage;
    }

    @FXML
    public double NMclassification(ActionEvent actionEvent) {
        int successfulGuess = 0;
        int totalProbes = testingSet.size();

        List<Vector<Double>> referenceAcerValues = Utils.getFeatureValues(trainingSet, "Acer", sfsResults);
        List<Vector<Double>> referenceQuercusValues = Utils.getFeatureValues(trainingSet, "Quercus", sfsResults);

        Vector<Double> acerAvg = MathOperations.calculateAverageVector(referenceAcerValues);
        Vector<Double> quercusAvg = MathOperations.calculateAverageVector(referenceQuercusValues);
        for(int iterator = 0; iterator < testingSet.size(); iterator++){
            Double acerAvgDistance = Utils.calculateDistance(testingSet.get(iterator), acerAvg, sfsResults);
            Double quercusAvgDistance = Utils.calculateDistance(testingSet.get(iterator), quercusAvg, sfsResults);
            if(acerAvgDistance < quercusAvgDistance) {
                if (testingSet.get(iterator).classType.contains("Acer"))
                    successfulGuess++;
            }
            if(quercusAvgDistance < acerAvgDistance){
                if (testingSet.get(iterator).classType.contains("Quercus"))
                    successfulGuess++;
            }
        }
        double percentage = 100*((double)successfulGuess/totalProbes);
        DataStats.getChildren().add(new Text("Successful guesses: " + successfulGuess + " out of " + testingSet.size() + '\n'));
        DataStats.getChildren().add(new Text("NM classificator quality: " + percentage + "%" + '\n'));
        return percentage;
    }

    @FXML
    public void NNCrossValidation(ActionEvent actionEvent) {
        int numberOfGroups = Integer.parseInt(GroupAmount.getText().toString());
        List groupTable[] = createGroupsForCrossValidation();
        double avgPercentage = 0;
        for(int index = 0; index < numberOfGroups; index++){
            testingSet.clear();
            trainingSet.clear();
            testingSet.addAll(groupTable[index]);
            for(int secondaryIndex = 0; secondaryIndex < numberOfGroups; secondaryIndex++){
                if(secondaryIndex != index)
                    trainingSet.addAll(groupTable[secondaryIndex]);
            }
            DataStats.getChildren().add(new Text("Group " + (index+1) + '\n'));
            avgPercentage+=NNclassification(actionEvent);
        }
        DataStats.getChildren().add(new Text("Average NN classificator quality: " + (avgPercentage/numberOfGroups) + "%" + '\n'));
    }

    @FXML
    public void NMCrossValidation(ActionEvent actionEvent) {
        int numberOfGroups = Integer.parseInt(GroupAmount.getText().toString());
        List groupTable[] = createGroupsForCrossValidation();
        double avgPercentage = 0;
        for(int index = 0; index < numberOfGroups; index++){
            testingSet.clear();
            trainingSet.clear();
            testingSet.addAll(groupTable[index]);
            for(int secondaryIndex = 0; secondaryIndex < numberOfGroups; secondaryIndex++){
                if(secondaryIndex != index)
                    trainingSet.addAll(groupTable[secondaryIndex]);
            }
            DataStats.getChildren().add(new Text("Group " + (index+1) + '\n'));
            avgPercentage+=NMclassification(actionEvent);
        }
        DataStats.getChildren().add(new Text("Average NM classificator quality: " + (avgPercentage/numberOfGroups) + "%" + '\n'));
    }

    @FXML
    public void kNNCrossValidation(ActionEvent actionEvent) {
        int numberOfGroups = Integer.parseInt(GroupAmount.getText().toString());
        List groupTable[] = createGroupsForCrossValidation();
        double avgPercentage = 0;
        for(int index = 0; index < numberOfGroups; index++){
            testingSet.clear();
            trainingSet.clear();
            testingSet.addAll(groupTable[index]);
            for(int secondaryIndex = 0; secondaryIndex < numberOfGroups; secondaryIndex++){
                if(secondaryIndex != index)
                    trainingSet.addAll(groupTable[secondaryIndex]);
            }
            DataStats.getChildren().add(new Text("Group " + (index+1) + '\n'));
            avgPercentage+=kNNclassification(actionEvent);
        }
        DataStats.getChildren().add(new Text("Average K-NN classificator quality: " + (avgPercentage/numberOfGroups) + "%" + '\n'));
    }

    public List[] createGroupsForCrossValidation() {
        List<Entry> acerData = selectAcer();
        List<Entry> quercusData = selectQuercus();
        int numberOfGroups = Integer.parseInt(GroupAmount.getText().toString());
        int acerGroupSize = acerData.size()/numberOfGroups;
        int quercusGroupSize = quercusData.size()/numberOfGroups;
        List groupTable[] = new List[numberOfGroups];
        for(int index = 0; index < numberOfGroups; index++){
            List tmpList = new ArrayList();
            for(int iterator = 0; iterator <= acerGroupSize; iterator++){
                tmpList.add(acerData.get((index*acerGroupSize)+iterator));
            }
            for(int iterator = 0; iterator <= quercusGroupSize; iterator++){
                tmpList.add(quercusData.get((index*quercusGroupSize)+iterator));
            }
            groupTable[index] = tmpList;
        }
        return groupTable;
    }
}
