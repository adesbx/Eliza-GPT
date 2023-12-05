package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for low coupling.
 */
public class MessagePattern {
    private Map<Pattern, Object> patternDictionary;

    private Matcher matcher;
    private String[] randomAnswer = new String[]{
            "Il faut beau aujourd'hui, vous ne trouvez pas ?",
            "Je ne comprends pas.",
            "Hmmm, hmm ...",
            "Qu'est-ce qui vous fait dire cela, %n ?",  //récup la réponse sans condition...
            "Qu'est-ce qui vous fait dire cela ?"
    };

    MessagePattern() {
        patternDictionary = new LinkedHashMap<>() {{
            put(Pattern.compile("Je m'appelle (.*)\\.", Pattern.CASE_INSENSITIVE), "Bonjour %g.");

            put(Pattern.compile("Au revoir\\.", Pattern.CASE_INSENSITIVE), "Au revoir...");

            put(Pattern.compile("Que fait une femme devant une page blanche \\?",
                    Pattern.CASE_INSENSITIVE), "elle lit ses droits...");

            Map<String, String> sousMap = Map.of("hasName", "Votre nom est %n.",
                    "hasNoName", "Je ne connais pas votre nom.");
            put(Pattern.compile("Quel est mon nom \\?", Pattern.CASE_INSENSITIVE), sousMap);
            put(Pattern.compile("Qui est le plus (.*) \\?", Pattern.CASE_INSENSITIVE),
                    "Le plus %g est bien sûr votre enseignant de MIF01 !");

            String[] answerWithJe = new String[]{
                    "Pourquoi dites-vous que %je ?",
                    "Pourquoi pensez-vous que %je ?",
                    "Êtes-vous sûr que %je ?"};
            put(Pattern.compile("(Je .*)\\.", Pattern.CASE_INSENSITIVE), answerWithJe);

            String[] answerWithQuestion = new String[]{
                    "Je vous renvoie la question ",
                    "Ici, c'est moi qui pose les\n" + "questions. "
            };
            put(Pattern.compile("(.*)\\?", Pattern.CASE_INSENSITIVE), answerWithQuestion);
        }};
    }

    Object getAnswer(final String message) {
        for (Map.Entry<Pattern, Object> entry : patternDictionary.entrySet()) {
            matcher = entry.getKey().matcher(message);
            if (matcher.matches()) {
                return entry.getValue();
            }
        }
        return randomAnswer;
    }

    Matcher getMatcher() {
        return matcher;
    }

}
