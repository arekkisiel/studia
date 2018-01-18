import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Utils {

    public static List<Entry> loadData(){
        List<Entry> listOfEntries = new ArrayList<>();

        CsvParserSettings settings = new CsvParserSettings();
        settings.getFormat().setLineSeparator("\n");
        CsvParser parser = new CsvParser(settings);
        List<String[]> allRows = parser.parseAll(getReader("E:\\repos\\studia\\SMPD-mvn\\src\\main\\resources\\Maple_Oak.txt"));
        for(String[] row:allRows.subList(1,allRows.size())){
            listOfEntries.add(new Entry(row));
        }
        return listOfEntries;
    }

    public static Reader getReader(final String path){
        Reader in = null;
        try {
            in = new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return in;
    }

    public static String countClassObjects(List<Entry> data, String classType) {
        int counter = 0;
        for (Entry object : data){
            if(object.classType.contains(classType))
                counter++;
        }
        return String.valueOf(counter);
    }

    public static List<Double> getFeatureValues(List<Entry> data, String classType, int featureId) {
        List<Double> resultList = new ArrayList<>();
        for (Entry object : data) {
            if (object.classType.contains(classType))
                resultList.add(object.getFeature(featureId).value);
        }
        return resultList;
    }

    public static List<Vector<Double>> getFeatureValues(List<Entry> data, String classType, List<Integer> featureIds) {
        List<Vector<Double>> resultList = new ArrayList<>();
        for(int featureId:featureIds){
            Vector<Double> tempVector = new Vector<>();
            for (Entry object : data) {
                if (object.classType.contains(classType))
                    tempVector.add(object.getFeature(featureId).value);
            }
            resultList.add(tempVector);
        }
        return resultList;
    }

    public static double calculateDistance(Entry testingEntry, Entry trainingEntry, List<Integer> features) {
        if(features.size() == 0)
            return 0;
        if(features.size() == 1)
            return testingEntry.getFeature(features.get(0)).value-trainingEntry.getFeature(features.get(0)).value;
        double sumOfSquaredDifferences = 0;
        for(int featureId : features)
            sumOfSquaredDifferences += Math.pow(testingEntry.getFeature(featureId).value-trainingEntry.getFeature(featureId).value, 2);
        return Math.sqrt(sumOfSquaredDifferences);
    }

    public static double calculateDistance(Entry testingEntry, Vector trainingEntry, List<Integer> features) {
        if(features.size() == 0)
            return 0;
        if(features.size() == 1)
            return testingEntry.getFeature(features.get(0)).value-Double.parseDouble(trainingEntry.get(features.get(0)).toString());
        double sumOfSquaredDifferences = 0;
        int iterator = 0;
        for(int featureId : features) {
            sumOfSquaredDifferences += Math.pow(testingEntry.getFeature(featureId).value - Double.parseDouble(trainingEntry.get(iterator).toString()), 2);
            iterator++;
        }
        return Math.sqrt(sumOfSquaredDifferences);
    }
}
