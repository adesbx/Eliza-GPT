package fr.univ_lyon1.info.m1.elizagpt.model;

import fr.univ_lyon1.info.m1.elizagpt.model.DataType;
import java.util.HashMap;
import java.util.Map;


public class DataApplication<T> {
    private Map<DataType, T> dataApplication;

    public DataApplication() {
        dataApplication = new HashMap<>();
    }

    public T get(DataType key){
        return dataApplication.get(key);
    }

    public void set(DataType key, T value) {
        dataApplication.put(key, value);
    }

    public String DataAnswer(String uncleanAnswer) {
        return null;
    }
}
