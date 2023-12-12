package fr.univ_lyon1.info.m1.elizagpt.model.Message;

import fr.univ_lyon1.info.m1.elizagpt.model.Dao.VerbDao;
import fr.univ_lyon1.info.m1.elizagpt.model.Data.DataApplication;
import fr.univ_lyon1.info.m1.elizagpt.model.Data.DataType;
import fr.univ_lyon1.info.m1.elizagpt.model.Pronouns;
import fr.univ_lyon1.info.m1.elizagpt.model.SelectAnswer.SelectAnswer;
import fr.univ_lyon1.info.m1.elizagpt.model.SelectAnswer.RandomAnswer;
import fr.univ_lyon1.info.m1.elizagpt.model.SelectAnswer.SimpleAnswer;
import fr.univ_lyon1.info.m1.elizagpt.model.SelectAnswer.ChoiceAnswer;

import java.util.Map;
import java.util.LinkedHashMap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for low coupling : add Answer.
 */
public class MessagePattern {
    private final Map<Pattern, SelectAnswer<String>> patternDictionary;

    private DataApplication<String> dataApplication;

    private Matcher matcher;

    private Pronouns pronouns = new Pronouns();

    private VerbDao verbDao = new VerbDao(pronouns);
    private static final RandomAnswer<String> RANDOM_ANSWER = new RandomAnswer<String>(new String[]{
            "Il fait beau aujourd'hui, vous ne trouvez pas ?",
            "Je ne comprends pas.",
            "Hmmm, hmm ...",
            "Qu'est-ce qui vous fait dire cela ?",
            "Il faut beau aujourd'hui, vous ne trouvez pas, $NAME ?",
            "Je ne comprends pas. $NAME",
            "Hmmm, hmm ... $NAME",
            "Qu'est-ce qui vous fait dire cela $NAME ?"
    });


    /**
     * Constructor of the class, here we initialise the patternDictionary with answer, response.
     * @param newDataApplication
     */
    public MessagePattern(final DataApplication<String> newDataApplication) {
        dataApplication = newDataApplication;

        patternDictionary = new LinkedHashMap<>() {{
            String regex1 = "Je m'appelle (.*)\\.";
            put(Pattern.compile(regex1, Pattern.CASE_INSENSITIVE),
                    new SimpleAnswer<String>("Bonjour $GROUP."));
            dataApplication.patternContainData(regex1, DataType.NAME);

            String regex2 = "Je me présente (.*)\\.";
            put(Pattern.compile(regex2, Pattern.CASE_INSENSITIVE),
                    new SimpleAnswer<String>("Salut $GROUP."));
            dataApplication.patternContainData(regex2, DataType.NAME);

            put(Pattern.compile("Au revoir\\.", Pattern.CASE_INSENSITIVE),
                    new SimpleAnswer<String>("Au revoir..."));

            put(Pattern.compile("Quel est mon nom \\?", Pattern.CASE_INSENSITIVE),
                    new ChoiceAnswer<String>("Votre nom est $NAME.", DataType.NAME,
                            "Je ne connais pas votre nom.", dataApplication));

            put(Pattern.compile("Qui est le plus (.*) \\?", Pattern.CASE_INSENSITIVE),
                    new SimpleAnswer<String>(
                            "Le plus $GROUP est bien sûr votre enseignant de MIF01 !"));

            String[] answerWithJe = new String[]{
                    "Pourquoi dites-vous que $GROUP ?",
                    "Pourquoi pensez-vous que $GROUP ?",
                    "Êtes-vous sûr que $GROUP ?"};
            put(Pattern.compile("(Je .*)\\.", Pattern.CASE_INSENSITIVE),
                    new RandomAnswer<String>(answerWithJe));

            String[] answerWithTu = new String[]{
                    "Pourquoi dites-vous que $GROUP ?",
                    "Pourquoi pensez-vous que $GROUP ?",
                    "Êtes-vous sûr que $GROUP ?"};
            put(Pattern.compile("(Tu .*)\\.", Pattern.CASE_INSENSITIVE),
                    new RandomAnswer<String>(answerWithTu));

            String[] answerWithVous = new String[]{
                    "Pourquoi dis-tu que $GROUP ?",
                    "Pourquoi penses-tu que $GROUP ?",
                    "Êtes-vous sûr que $GROUP ?"};
            put(Pattern.compile("(Vous .*)\\.", Pattern.CASE_INSENSITIVE),
                    new RandomAnswer<String>(answerWithVous));

            String[] answerWithQuestion = new String[]{
                    "Je vous renvoie la question ",
                    "Ici, c'est moi qui pose les\n" + "questions. "
            };

            put(Pattern.compile("Quelle est la météo \\?", Pattern.CASE_INSENSITIVE),
                    new SimpleAnswer<String>(weatherAdapter.getResults()));

            put(Pattern.compile("(.*)\\?", Pattern.CASE_INSENSITIVE),
                    new RandomAnswer<String>(answerWithQuestion));
        }};
    }

    /**
     * Get the DataType.
     * @param answerWithData the answer without the data replace
     * @return DataType
     */
    private DataType getDataType(final String answerWithData) {
        for (DataType dataType : DataType.values()) {
            String toReplace = "$".concat(dataType.name());
            if (answerWithData.contains(toReplace)) {
                return dataType;
            }
        }
        return null;
    }

    /**
     * function for choosing a random Answer with priority to answerWithData.
     * @return String the answer
     */
    private String choiceRandomAnswer() {
        String randomAnswer = RANDOM_ANSWER.execute();
        if (getDataType(randomAnswer) != null) {
            DataType dataType = getDataType(randomAnswer);
            if (dataApplication.get(dataType) != null) {
                return randomAnswer;
            } else {
                return choiceRandomAnswer();
            }
        }
            return randomAnswer;
    }

    /**
     * return the finalAnswer.
     * @param message
     * @return String the string you want eliza to say
     */
    public String getAnswer(final String message) {
        String finalAnswer = null;
        for (Map.Entry<Pattern, SelectAnswer<String>> entry
                : patternDictionary.entrySet()) {
            matcher = entry.getKey().matcher(message);
            if (matcher.matches()) {
                dataApplication.addInData(entry.getKey().toString(), matcher);
                finalAnswer = entry.getValue().execute();
                break;
            }
        }
        if (finalAnswer == null) {
            finalAnswer = choiceRandomAnswer();
        }
        if (finalAnswer.contains("$GROUP")) {
            String conjugatedMatcher = pronouns.replaceOppositePronoun(
                    verbDao.conjugateVerb(matcher.group(1)));
            return finalAnswer.replace("$GROUP", conjugatedMatcher);
        }
        return finalAnswer;
    }
}
