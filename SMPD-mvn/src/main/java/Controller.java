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
            //DataStats.getChildren().add(new Text("Fisher for feature " + counter + ": " + result + '\n'));
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
                //DataStats.getChildren().add(new Text("Fisher for combination " + combination + ": " + result + '\n'));
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
    public void train(ActionEvent actionEvent) {
        trainingSet.clear();
        testingSet.clear();
        int trainingSetSize = Integer.parseInt(SetSize3.getText());
        if(trainingSetSize % 2 == 0){
            selectAcer(trainingSetSize/2);
            selectQuercus(trainingSetSize/2);
        }
        else{
            selectAcer((trainingSetSize-1)/2);
            selectQuercus((trainingSetSize+1)/2);
        }
        DataStats.getChildren().add(new Text("Training set size: " + trainingSet.size() + '\n'));
        DataStats.getChildren().add(new Text("Testing set size: " + testingSet.size() + '\n'));
    }

    private void selectQuercus(int trainingSetSize) {
        int addedProbes = 0;
        for(Entry entry : data){
            if(addedProbes<trainingSetSize){
                if(entry.classType.contains("Quercus")){
                    trainingSet.add(entry);
                    addedProbes++;
                }
            }
            else{
                if(entry.classType.contains("Quercus"))
                    testingSet.add(entry);
            }
        }
    }

    private void selectAcer(int trainingSetSize) {
        int addedProbes = 0;
        for(Entry entry : data){
            if(addedProbes<trainingSetSize){
                if(entry.classType.contains("Acer")){
                    trainingSet.add(entry);
                    addedProbes++;
                }
            }
            else{
                if(entry.classType.contains("Acer"))
                    testingSet.add(entry);
            }
        }
    }

    @FXML
    public void NNclassification(ActionEvent actionEvent) {
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
            //DataStats.getChildren().add(new Text("Probe number " + iterator + " was classified as " + assignedClassType + " and correct result is " + testingSet.get(iterator).classType + '\n'));
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
    }

    @FXML
    public void kNNclassification(ActionEvent actionEvent) {
        int successfulGuess = 0;
        int totalProbes = testingSet.size();
        for(int iterator = 0; iterator < totalProbes; iterator++){
            Pair distanceTable[] = new Pair[trainingSet.size()];
            for(int secondIterator = 0; secondIterator < trainingSet.size(); secondIterator++){
                distanceTable[secondIterator] = new Pair<>(trainingSet.get(secondIterator).classType,Utils.calculateDistance(testingSet.get(iterator), trainingSet.get(secondIterator), sfsResults));
            }
            Pair sortedDistanceTable [] = Utils.bubbleSort(distanceTable);
            //DataStats.getChildren().add(new Text("Probe number " + iterator + " was classified as " + assignedClassType + " and correct result is " + testingSet.get(iterator).classType + '\n'));
            int k = Integer.parseInt(K.getText());
            int acerGuess = 0;
            int quercusGuess = 0;
            for(Pair entry : sortedDistanceTable){
                if(acerGuess < k && quercusGuess < k) {
                    if (entry.getKey().toString().contains("Acer"))
                        acerGuess++;
                    if (entry.getKey().toString().contains("Quercus"))
                        quercusGuess++;
                }
            }
            String assignedClassType;
            if(acerGuess == k)
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
    }

    @FXML
    public void NMclassification(ActionEvent actionEvent) {
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
    }

    @FXML
    public void NNCrossValidation(ActionEvent actionEvent) {
        int numberOfGroups = Integer.parseInt(GroupAmount.getText().toString());
        int numberOfBiggerGroups = data.size() % numberOfGroups;
        int biggerGroupSize = ((data.size() - numberOfBiggerGroups) / numberOfGroups)+1;
        List groupTable[] = new List[numberOfGroups];
        for(int index = 0; index < numberOfBiggerGroups; index++){
            List tmpList = new ArrayList();
            for(int iterator = 0; iterator < biggerGroupSize; iterator++){
                tmpList.add(data.get((index*biggerGroupSize)+iterator));
            }
            groupTable[index] = tmpList;
        }
        for(int index = numberOfBiggerGroups; index < numberOfGroups; index++){
            List tmpList = new ArrayList();
            for(int iterator = 0; iterator < (biggerGroupSize-1); iterator++){
                tmpList.add(data.get(((numberOfBiggerGroups*biggerGroupSize)+(index-numberOfBiggerGroups)*(biggerGroupSize-1))+iterator));
            }
            groupTable[index] = tmpList;
        }
        for(int index = 0; index < numberOfGroups; index++){
            testingSet.clear();
            trainingSet.clear();
            trainingSet.addAll(groupTable[index]);
            for(int secondaryIndex = 0; secondaryIndex < numberOfGroups; secondaryIndex++){
                if(secondaryIndex != index)
                    testingSet.addAll(groupTable[secondaryIndex]);
            }
            DataStats.getChildren().add(new Text("Group " + (index+1) + '\n'));
            NNclassification(actionEvent);
        }
    }

    @FXML
    public void NMCrossValidation(ActionEvent actionEvent) {
        int numberOfGroups = Integer.parseInt(GroupAmount.getText().toString());
        int numberOfBiggerGroups = data.size() % numberOfGroups;
        int biggerGroupSize = ((data.size() - numberOfBiggerGroups) / numberOfGroups)+1;
        List groupTable[] = new List[numberOfGroups];
        for(int index = 0; index < numberOfBiggerGroups; index++){
            List tmpList = new ArrayList();
            for(int iterator = 0; iterator < biggerGroupSize; iterator++){
                tmpList.add(data.get((index*biggerGroupSize)+iterator));
            }
            groupTable[index] = tmpList;
        }
        for(int index = numberOfBiggerGroups; index < numberOfGroups; index++){
            List tmpList = new ArrayList();
            for(int iterator = 0; iterator < (biggerGroupSize-1); iterator++){
                tmpList.add(data.get(((numberOfBiggerGroups*biggerGroupSize)+(index-numberOfBiggerGroups)*(biggerGroupSize-1))+iterator));
            }
            groupTable[index] = tmpList;
        }
        for(int index = 0; index < numberOfGroups; index++){
            testingSet.clear();
            trainingSet.clear();
            trainingSet.addAll(groupTable[index]);
            for(int secondaryIndex = 0; secondaryIndex < numberOfGroups; secondaryIndex++){
                if(secondaryIndex != index)
                    testingSet.addAll(groupTable[secondaryIndex]);
            }
            DataStats.getChildren().add(new Text("Group " + index+1 + '\n'));
            NMclassification(actionEvent);
        }
    }

    @FXML
    public void kNNCrossValidation(ActionEvent actionEvent) {
        int numberOfGroups = Integer.parseInt(GroupAmount.getText().toString());
        int numberOfBiggerGroups = data.size() % numberOfGroups;
        int biggerGroupSize = ((data.size() - numberOfBiggerGroups) / numberOfGroups)+1;
        List groupTable[] = new List[numberOfGroups];
        for(int index = 0; index < numberOfBiggerGroups; index++){
            List tmpList = new ArrayList();
            for(int iterator = 0; iterator < biggerGroupSize; iterator++){
                tmpList.add(data.get((index*biggerGroupSize)+iterator));
            }
            groupTable[index] = tmpList;
        }
        for(int index = numberOfBiggerGroups; index < numberOfGroups; index++){
            List tmpList = new ArrayList();
            for(int iterator = 0; iterator < (biggerGroupSize-1); iterator++){
                tmpList.add(data.get(((numberOfBiggerGroups*biggerGroupSize)+(index-numberOfBiggerGroups)*(biggerGroupSize-1))+iterator));
            }
            groupTable[index] = tmpList;
        }
        for(int index = 0; index < numberOfGroups; index++){
            testingSet.clear();
            trainingSet.clear();
            trainingSet.addAll(groupTable[index]);
            for(int secondaryIndex = 0; secondaryIndex < numberOfGroups; secondaryIndex++){
                if(secondaryIndex != index)
                    testingSet.addAll(groupTable[secondaryIndex]);
            }
            DataStats.getChildren().add(new Text("Group " + index+1 + '\n'));
            kNNclassification(actionEvent);
        }
    }
}
