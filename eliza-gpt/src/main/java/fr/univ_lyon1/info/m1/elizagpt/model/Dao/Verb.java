package fr.univ_lyon1.info.m1.elizagpt.model.Dao;

import java.util.Collection;
import java.util.Set;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Information about conjugation of a verb.
 */
public class Verb {
    private Map<String, String> verbMap;

    /**
     * constructor by default of Verb.
     */
    public Verb() {
        verbMap = new HashMap<>();
    }

    /**
     * constructor with header of the csv file.
     *
     * @param header the header
     */
    public Verb(final List<String> header) {
        verbMap = new HashMap<>();
        for (String key : header) {
            verbMap.put(key, null);
        }
    }

    /**
     * return the id of the verb.
     *
     * @return id
     */
    public String getId() {
        return verbMap.get("infinitive");
    }

    /**
     * get a specific conjugaison.
     *
     * @param header the header
     * @return by header
     */
    public String getByHeader(final String header) {
        return verbMap.get(header);
    }

    /**
     * get a set of all the key.
     *
     * @return key
     */
    public Set<String> getKey() {
        return this.verbMap.keySet();
    }

    /**
     * replace the value at the key.
     *
     * @param key   the key
     * @param value the value
     */
    public void replace(final String key, final String value) {
        this.verbMap.replace(key, value);
    }

    /**
     * return all the conjugaison of the current verb.
     *
     * @return ligne
     */
    public Collection<String> getLigne() {
        return this.verbMap.values();
    }

    /**
     * get the Collection of the map for iterate on it.
     *
     * @return verb map
     */
    public Collection<Map.Entry<String, String>> getVerbMap() {
        return verbMap.entrySet();
    }

    /**
     * change the first verb to the second conjugaison.
     *
     * @param header  the header
     * @param header2 the header 2
     * @param text    the text
     * @return string
     */
    public String changePerson(final String header, final String header2, final String text) {
        String processedText = text
                .replaceAll("[Jj]e ([a-z]*)e ", "vous $1ez ");
            processedText = processedText.replaceAll(
                    verbMap.get(header),
                    verbMap.get(header2));

        processedText = processedText
                .replaceAll("[Jj]e ([a-z]*)s ", "vous $1ssez ")
                .replace("mon ", "votre ")
                .replace("ma ", "votre ")
                .replace("mes ", "vos ")
                .replace("moi", "vous");
        return processedText;
    }
}
