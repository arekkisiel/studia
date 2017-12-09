import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MathOperations {

    public static double calculateFisher(List<Entry> data, int featureId){
        List<Double> acerValues = Utils.getFeatureValues(data, "Acer", featureId);
        List<Double> quercusValues = Utils.getFeatureValues(data, "Quercus", featureId);
        double acerAvg = calculateAverage(acerValues);
        double quercusAvg = calculateAverage(quercusValues);
        double acerStd = calculateStd(acerValues, acerAvg);
        double quercusStd = calculateStd(quercusValues, quercusAvg);
        return Math.abs(acerAvg-quercusAvg)/(acerStd+quercusStd);
    }

    public static double calculateFisher(List<Entry> data, List<Integer> featureIds){
        List<Vector<Double>> acerValues = Utils.getFeatureValues(data, "Acer", featureIds);
        List<Vector<Double>> quercusValues = Utils.getFeatureValues(data, "Quercus", featureIds);
        Vector<Double> acerAvg = calculateAverageVector(acerValues);
        Vector<Double> quercusAvg = calculateAverageVector(quercusValues);
        Vector<Vector<Double>> acerCovarianceMatrix = calculateCovarianceMatrix(acerValues, acerAvg);
        Vector<Vector<Double>> quercusCovarianceMatrix = calculateCovarianceMatrix(quercusValues, quercusAvg);
        double acerDeterminant = determinant(convertMatrix(acerCovarianceMatrix));
        double quercusDeterminant = determinant(convertMatrix(quercusCovarianceMatrix));
        double vectorLength = calculateLength(subtractVectors(acerAvg, quercusAvg));
        return vectorLength/(acerDeterminant+quercusDeterminant);
    }

    private static double calculateLength(Vector<Double> vector) {
        double result = 0;
        for(double element:vector){
            result += Math.pow(element, 2);
        }
        return Math.sqrt(result);
    }


    private static Vector<Double> subtractVectors(Vector<Double> acerAvg, Vector<Double> quercusAvg) {
        Vector<Double> resultingVector = new Vector<>();
        for(int index = 0; index < acerAvg.size(); index++){
            resultingVector.add(acerAvg.get(index)-quercusAvg.get(index));
        }
        return resultingVector;
    }
    public static double[][] convertMatrix(Vector<Vector<Double>> inputMatrix) {
        double[][] matrix = new double[inputMatrix.size()][inputMatrix.size()];
        for(int horizontal = 0; horizontal < inputMatrix.size(); horizontal++){
            for(int vertical = 0; vertical < inputMatrix.get(horizontal).size(); vertical++){
                matrix[horizontal][vertical] = inputMatrix.get(horizontal).get(vertical);
            }
        }
        return matrix;
    }
    // http://professorjava.weebly.com/matrix-determinant.html
    public static double determinant(double[][] matrix){
        double sum=0;
        int s;
        if(matrix.length==1){
            return(matrix[0][0]);
        }
        for(int i=0;i<matrix.length;i++){
            double[][]smaller= new double[matrix.length-1][matrix.length-1];
            for(int a=1;a<matrix.length;a++){
                for(int b=0;b<matrix.length;b++){
                    if(b<i){
                        smaller[a-1][b]=matrix[a][b];
                    }
                    else if(b>i){
                        smaller[a-1][b-1]=matrix[a][b];
                    }
                }
            }
            if(i%2==0){
                s=1;
            }
            else{
                s=-1;
            }
            sum+=s*matrix[0][i]*(determinant(smaller));
        }
        return(sum);
    }

    private static Vector<Vector<Double>> calculateCovarianceMatrix(List<Vector<Double>> values, Vector<Double> avg) {
        Vector<Vector<Double>> differenceMatrix = calculateDifferenceMatrix(values, avg);
        Vector<Vector<Double>> transposedDifferenceMatrix = transposeMatrix(differenceMatrix);
        Vector<Vector<Double>> multipliedMatrix = multiplyMatrices(differenceMatrix, transposedDifferenceMatrix);
        return divideMatrix(multipliedMatrix, values.get(0).size());
    }

    private static Vector<Vector<Double>> divideMatrix(Vector<Vector<Double>> matrix, int quotient) {
        Vector<Vector<Double>> resultingMatrix = new Vector<>();
        for(Vector<Double> column:matrix){
            Vector<Double> tempColumn = new Vector<>();
            for(Double value:column){
                tempColumn.add(value/quotient);
            }
            resultingMatrix.add(tempColumn);
        }
        return resultingMatrix;
    }

    private static Vector<Vector<Double>> multiplyMatrices(Vector<Vector<Double>> firstMatrix, Vector<Vector<Double>> secondMatrix) {
        Vector<Vector<Double>> resultingMatrix = new Vector<>();
        for(int column = 0; column < firstMatrix.size(); column++) {
            Vector<Double> tempVector = new Vector<>();
            for (int horizontal = 0; horizontal < firstMatrix.size(); horizontal++) {

                double tempSum = 0;
                for (int vertical = 0; vertical < firstMatrix.get(horizontal).size(); vertical++) {
                    tempSum += firstMatrix.get(horizontal).get(vertical) * secondMatrix.get(vertical).get(column);
                }
                tempVector.add(tempSum);
            }
            resultingMatrix.add(tempVector);
        }
        return resultingMatrix;
    }

    private static Vector<Vector<Double>> transposeMatrix(Vector<Vector<Double>> matrix) {
        Vector<Vector<Double>> transposedMatrix = new Vector<>();
        for(int horizontal = 0; horizontal < matrix.get(0).size(); horizontal++){
            Vector<Double> tempVector = new Vector<>();
            for(Vector<Double> feature : matrix){
                tempVector.add(feature.get(horizontal));
            }
            transposedMatrix.add(tempVector);
        }
        return transposedMatrix;
    }

    private static Vector<Vector<Double>> calculateDifferenceMatrix(List<Vector<Double>> values, Vector<Double> avg) {
        Vector<Vector<Double>> differenceMatrix = new Vector<>();
        for(int featureIndex = 0; featureIndex < values.size(); featureIndex++){
            Vector<Double> tempVector = new Vector<>();
            for(Double value : values.get(featureIndex)){
                tempVector.add(value-avg.get(featureIndex));
            }
            differenceMatrix.add(tempVector);
        }
        return differenceMatrix;
    }

    private static double calculateStd(List<Double> values, double average) {
        double temp = 0;
        for(double value : values)
            temp += Math.pow((value-average), 2);
        return Math.sqrt(temp/(values.size()));
    }

    private static double calculateAverage(List<Double> values) {
        double sum = 0;
        for(double object:values)
            sum+=object;
        return sum/values.size();
    }

    private static Vector<Double> calculateAverageVector(List<Vector<Double>> values) {
        Vector<Double> avgVector = new Vector<>();
        for(Vector<Double> featureValues : values) {
            double sum = 0;
            for (int index = 0; index < featureValues.size(); index++) {
                sum += featureValues.get(index);
            }
            avgVector.add(sum / featureValues.size());
        }
        return avgVector;
    }

    static List<List<Integer>> defineCombinations(int allFeaturesCount, int subsetSize) {

        List<List<Integer>> definedCombinations = new ArrayList<>();

        ArrayList<Integer> currentCombination = new ArrayList<>();
        completeCombination(allFeaturesCount, subsetSize, 0, currentCombination, definedCombinations); // because it need to begin from 1

        return definedCombinations;
    }

    private static void completeCombination(int allFeaturesCount, int subsetSize, int start, List<Integer> currentCombination, List<List<Integer>> definedCombinations) {
        if (currentCombination.size() == subsetSize) {
            definedCombinations.add(new ArrayList<Integer>(currentCombination));
            return;
        }

        for (int i = start; i < allFeaturesCount; i++) {
            currentCombination.add(i);
            completeCombination(allFeaturesCount, subsetSize, i + 1, currentCombination, definedCombinations);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }
}
