package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.HashMap;
import java.util.Map;


/**
 * Contain all variable grab from the chat.
 * @param <T>
 */
public class DataApplication<T> {
    private Map<DataType, T> dataApplication;

    /**
     * constructor of class by hashMap.
     */
    public DataApplication() {
        dataApplication = new HashMap<>();
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
     * set value with the key.
     * @param key
     * @param value
     */
    public void set(final DataType key, final T value) {
        dataApplication.put(key, value);
    }
}
