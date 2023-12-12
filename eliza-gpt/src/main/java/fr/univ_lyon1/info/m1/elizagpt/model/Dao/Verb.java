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

    public Verb() {
        verbMap = new HashMap<>();
    }
    public Verb(List<String> enTetes) {
        verbMap = new HashMap<>();
        for (String key : enTetes) {
            verbMap.put(key, null);
        }
    }

    public String getId() {
        return verbMap.get("infinitive");
    }

    public Set<String> getKey() {
        return this.verbMap.keySet();
    }

    public void replace(String key, String value) {
        this.verbMap.replace(key, value);
    }

    public Collection<String> getLigne() {
        return this.verbMap.values();
    }

    /**
     * Turn a 1st-person sentence (Je ...) into a plural 2nd person (Vous ...).
     * The result is not capitalized to allow forming a new sentence.
     *
     * @param text
     * @return The 2nd-person sentence.
     */
    public String firstToSecondPerson(final String text) {
        String processedText = text
                .replaceAll("[Jj]e ([a-z]*)e ", "vous $1ez ");
        for (Map.Entry<String, String> entry
                : verbMap.entrySet()) {
            processedText = processedText.replaceAll(
                    "[Jj]e " + entry.getKey(),
                    "vous " + entry.getValue());
        }
        processedText = processedText
                .replaceAll("[Jj]e ([a-z]*)s ", "vous $1ssez ")
                .replace("mon ", "votre ")
                .replace("ma ", "votre ")
                .replace("mes ", "vos ")
                .replace("moi", "vous");
        return processedText;
    }
}
