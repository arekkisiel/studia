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
        List<String[]> allRows = parser.parseAll(getReader("G:\\repos\\studia\\SMPD-mvn\\src\\main\\resources\\Maple_Oak.txt"));
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
}
