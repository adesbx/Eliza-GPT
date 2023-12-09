package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;


/**
 * Contain all variable grab from the chat.
 * @param <T>
 */
public class DataApplication<T> {
    private Map<DataType, T> dataApplication;

    private Map<DataType, List<String>> grabData = new HashMap<>();

    /**
     * constructor of class by hashMap.
     */
    public DataApplication() {
        dataApplication = new HashMap<>();
        for (DataType dataType : DataType.values()) {
            grabData.put(dataType, new ArrayList<>());
            // grabData permit to know if a pattern is associated with a variable
        }
    }

    /**
     * get from the hashMap.
     * @param key
     * @return
     */
    public T get(final DataType key) {
        return dataApplication.get(key);
    }

    /**
     * identify a pattern to grab data from.
     */
    public void patternContainData(final String regex, final DataType dataType) {
        grabData.get(dataType).add(regex);
    }

    /**
     * add DATA memory when the pattern is in grabdata.
     * @param key
     */
    public void addInData(final String key, final Matcher matcher) {
        for (Map.Entry<DataType, List<String>> entry : grabData.entrySet()) {
            if (entry.getValue().contains(key)) {
                dataApplication.put(entry.getKey(), (T) matcher.group(1));
            }
        }
    }
}
