package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Information about conjugation of a verb.
 */
public class Verb {

    /**
     * List of 3rd group verbs and their correspondence from 1st person singular
     * (Je) to 2nd person plural (Vous).
     */
    protected static final Map<String, String> VERBS = new HashMap<>() {{
        put("suis", "êtes");
        put("vais", "allez");
        put("peux", "pouvez");
        put("dois", "devez");
        put("dis", "dites");
        put("ai", "avez");
        put("fais", "faites");
        put("sais", "savez");
    }};;


    Verb() {
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
                : VERBS.entrySet()) {
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
