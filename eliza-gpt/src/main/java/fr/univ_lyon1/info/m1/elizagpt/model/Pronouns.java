package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for treat pronouns.
 * in key we follow header of the files
 */
public class Pronouns {
    private final Map<String, List<String>> pronounsMap;

    /**
     * constructor of the hashmap.
     */
    public Pronouns() {
        pronounsMap = new HashMap<>() {{
            put("first person singular", new ArrayList<String>(
                    Arrays.asList("je", "j'", "je m'")));
            put("second person singular", new ArrayList<String>(
                    Arrays.asList("tu", "tu t'")));
            put("third person singular", new ArrayList<String>(
                    Arrays.asList("il", "elle", "on", "il s'", "elle s'", "on s'")));
            put("first person plural", new ArrayList<String>(
                    Arrays.asList("nous", "nous nous")));
            put("second person plural", new ArrayList<String>(
                    Arrays.asList("vous", "vous vous")));
            put("third person plural", new ArrayList<String>(
                    Arrays.asList("ils", "elles", "ont", "ils s'", "elles s'", "ont s'")));
        }};
    }

    /**
     * process the opposite of the pronouns header.
     *
     * @param pronouns
     * @return String
     */
    public String getOppositeHeader(final String pronouns) {
        switch (pronouns) {
            case "first person singular":  // Je vers Vous
                return "second person plural";
            //return "second person singular";
            case "second person singular":  // Tu vers Je
                return "first person singular";
            case "second person plural":  // Vous vers Je
                return "first person singular";
            default:
                return pronouns;
        }
    }

    /**
     * return the header pronouns identify.
     *
     * @param text
     * @return String
     */
    public String getPronounHeader(final String text) {
        String[] splitedText = text.split("\\b");
        for (String word : splitedText) {
            for (Map.Entry<String, List<String>> entry
                    : this.pronounsMap.entrySet()) {
                if (entry.getValue().contains(word.toLowerCase())) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    /**
     * remplace in the text all pronouns with the opposite.
     *
     * @param text
     * @return String
     */
    public String replaceOppositePronoun(final String text) {
        String firstPronounHeader = getPronounHeader(text);
        if (firstPronounHeader == null) {
            return text;
        }
        String secondPronounHeader = getOppositeHeader(firstPronounHeader);
        String[] splitedText = text.split("\\b");
        for (String word : splitedText) {
            if (this.pronounsMap.get(firstPronounHeader).contains(word.toLowerCase())) {
                return text.replaceAll(word, this.pronounsMap.get(secondPronounHeader).get(0));
            }
        }
        return text;
    }
}
