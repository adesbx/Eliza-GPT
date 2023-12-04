package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Class for low coupling.
 *
 */
public class MessagePattern {
    private Map<Pattern, Object> patternDictionary;
    private String[] randomAnswer = new String[]{
            "Il faut beau aujourd'hui, vous ne trouvez pas ?",
            "Je ne comprends pas.",
            "Hmmm, hmm ...",
            "Qu'est-ce qui vous fait dire cela, %n ?",
            "Qu'est-ce qui vous fait dire cela ?"
    };
    MessagePattern() {
        patternDictionary = new HashMap<>() {{
            put(Pattern.compile("Je m'appelle (.*)\\.", Pattern.CASE_INSENSITIVE), "Bonjour %n.");
            Map<String, String> sousMap = Map.of("hasName", "Votre nom est %n.",
                    "hasNoName", "Je ne connais pas votre nom.");
            put(Pattern.compile("Quel est mon nom \\?", Pattern.CASE_INSENSITIVE), sousMap);
            put(Pattern.compile("Qui est le plus (.*) \\?", Pattern.CASE_INSENSITIVE),
                    "Le plus %n est bien sûr votre enseignant de MIF01 !");
            String[] answerWithJe = new String[]{
                    "Pourquoi dites-vous que ",
                    "Pourquoi pensez-vous que ",
                    "Êtes-vous sûr que "};
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
            Matcher matcher = entry.getKey().matcher(message);
            if (matcher.matches()) {
                return entry.getValue();
            }
        }
        return randomAnswer;
    }
}
