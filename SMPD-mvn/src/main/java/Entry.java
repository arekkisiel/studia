import java.util.ArrayList;
import java.util.List;

public class Entry {
    String classType;
    List<Feature> features;

    public Entry(final String[] row){
        this.classType = row[0];
        List<Feature> temp = new ArrayList<>();
        for (int index = 1; index < row.length; index++){
            temp.add(new Feature(index-1, row[index]));
        }
        this.features = temp;
    }

    public Feature getFeature(int featureId){
        for(Feature object:features){
            if(object.id == featureId)
                return object;
        }
        return null;
    }
}



class Feature {
    int id;
    double value;

    public Feature(int id, String value) {
        this.id = id;
        this.value = Double.parseDouble(value);
    }
}
