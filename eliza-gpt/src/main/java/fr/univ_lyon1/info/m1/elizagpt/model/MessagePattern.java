package fr.univ_lyon1.info.m1.elizagpt.model;

import fr.univ_lyon1.info.m1.elizagpt.model.SelectAnswer.*;

import fr.univ_lyon1.info.m1.elizagpt.model.DataType;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for low coupling.
 */
public class MessagePattern {
    private Map<Pattern, SelectAnswer<String>> patternDictionary;

    private DataApplication<String> dataApplication;

    private Matcher matcher;
    private RandomAnswer<String> randomAnswer = new RandomAnswer<String>(new String[]{
            "Il faut beau aujourd'hui, vous ne trouvez pas ?",
            "Je ne comprends pas.",
            "Hmmm, hmm ...",
            "Qu'est-ce qui vous fait dire cela, %n ?",  //récup la réponse sans condition...
            "Qu'est-ce qui vous fait dire cela ?"
    });

    MessagePattern(DataApplication<String> newDataApplication) {
        dataApplication = newDataApplication;

        patternDictionary = new LinkedHashMap<>() {{
            put(Pattern.compile("Je m'appelle (.*)\\.", Pattern.CASE_INSENSITIVE), new SimpleAnswer<String>("Bonjour %g."));

            put(Pattern.compile("Au revoir\\.", Pattern.CASE_INSENSITIVE), new SimpleAnswer<String>("Au revoir..."));

            put(Pattern.compile("Quel est mon nom \\?", Pattern.CASE_INSENSITIVE), new ChoiceAnswer<String>("Votre nom est %n.", DataType.NAME, "Je ne connais pas votre nom.", dataApplication));

            put(Pattern.compile("Qui est le plus (.*) \\?", Pattern.CASE_INSENSITIVE),
                    new SimpleAnswer<String>("Le plus %g est bien sûr votre enseignant de MIF01 !"));

            String[] answerWithJe = new String[]{
                    "Pourquoi dites-vous que %je ?",
                    "Pourquoi pensez-vous que %je ?",
                    "Êtes-vous sûr que %je ?"};
            put(Pattern.compile("(Je .*)\\.", Pattern.CASE_INSENSITIVE), new RandomAnswer<String>(answerWithJe));

            String[] answerWithQuestion = new String[]{
                    "Je vous renvoie la question ",
                    "Ici, c'est moi qui pose les\n" + "questions. "
            };
            put(Pattern.compile("(.*)\\?", Pattern.CASE_INSENSITIVE), new RandomAnswer<String>(answerWithQuestion));
        }};
    }

    String getAnswer(final String message) {
        for (Map.Entry<Pattern, SelectAnswer<String>> entry : patternDictionary.entrySet()) {
            matcher = entry.getKey().matcher(message);
            if (matcher.matches()) {
                return entry.getValue().execute();
            }
        }

        return randomAnswer.execute();
    }

    Matcher getMatcher() {
        return matcher;
    }

}
