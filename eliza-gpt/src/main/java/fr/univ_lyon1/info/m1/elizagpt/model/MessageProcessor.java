package fr.univ_lyon1.info.m1.elizagpt.model;

//import javafx.scene.control.Label;
import fr.univ_lyon1.info.m1.elizagpt.data.Data;
import fr.univ_lyon1.info.m1.elizagpt.observer.ProcessorObserver;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Logic to process a message (and probably reply to it).
 */

public class MessageProcessor {
    private final Random random = new Random();
    private final List<Data> dataList = new ArrayList<>(); // Liste de message
    private ProcessorObserver observer = null;
    private String name = null; //variable tmp pour stocker le nom.

    /**
     * Créé un nouveau observer.
     *
     * @param newObserver
     */
    public void attachObserver(final ProcessorObserver newObserver) {
        observer = newObserver;
    }

    /**
     * Met a jour l'observer.
     *
     */
    public void notifyObservers() {
        observer.processorUpdated();
    }

    /**
     * constructor of processor
     */
    public void beginConversation() {
        dataList.add(new Data("Bonjour", true, dataList.size()+1));//TODO le hashCode n'est pas Bon là
        notifyObservers();
    }

    public void removeFromDataList(final int id) {
        Optional<Data> objFind = dataList.stream().filter(objet -> objet.getHashCode() == id).findFirst();
        objFind.ifPresent(obj -> {
            System.out.println("Object to remove : " + obj.getMessage());
            dataList.remove(obj);
        });
    }

    /**
     * Normlize the text: remove extra spaces, add a final dot if missing.
     *
     * @param text
     * @return normalized text.
     */
    public Data normalize(final Data text) {
        return new Data(text.getMessage().replaceAll("\\s+", " ")
                .replaceAll("^\\s+", "")
                .replaceAll("\\s+$", "")
                .replaceAll("[^\\.!?:]$", "$0."), text.getIsAnswer(), text.getHashCode());
    }

    /**
     * Recupère la dernière réponse du robot.
     *
     * @return réponse du robot.
     */
    public Data lastResponse() {
        for (Data data : dataList) {
            System.out.println(data.getMessage());
            System.out.println("\n");
        }
        return dataList.get(dataList.size() - 1);
    }

    public int getSize() {for (Data data : dataList) {
            System.out.println(data.getMessage());
            System.out.println("\n");
        }
        return dataList.size();
    }

    public Data get(final int id) {
        return dataList.get(id);
    }

    /**
     * Traite le message envoyé par l'utilisateur.
     * @param normalizedText
     */
    public void easyAnswer(final Data normalizedText) {
        System.out.println("id normalizedText model" + normalizedText.getHashCode());
        dataList.add(new Data(normalizedText.getMessage(), false, normalizedText.getHashCode()));

        Pattern pattern;
        Matcher matcher;

        // First, try to answer specifically to what the user said
        pattern = Pattern.compile(".*Je m'appelle (.*)\\.", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(normalizedText.getMessage());
        if (matcher.matches()) {
            name = matcher.group(1);
            final String answer = "Bonjour " + matcher.group(1) + ".";
            dataList.add(new Data(answer, true, dataList.size()+1));
            notifyObservers();

            return;
        }
        pattern = Pattern.compile("Quel est mon nom \\?", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(normalizedText.getMessage());
        if (matcher.matches()) {
            if (name != null) {
                final String answer = "Votre nom est " + name + ".";
                dataList.add(new Data(answer, true, dataList.size()+1));
                notifyObservers();
            } else {
                final String answer = "Je ne connais pas votre nom.";
                dataList.add(new Data(answer, true, dataList.size()+1));
                notifyObservers();
            }
            return;
        }
        pattern = Pattern.compile("Qui est le plus (.*) \\?", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(normalizedText.getMessage());
        if (matcher.matches()) {
            final String answer = "Le plus " + matcher.group(1) + " est bien sûr votre enseignant de MIF01 !";
            dataList.add(new Data(
                    answer,
                    true, dataList.size()+1)
            );
            notifyObservers();
            return;
        }
        pattern = Pattern.compile("(Je .*)\\.", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(normalizedText.getMessage());
        if (matcher.matches()) {
            final String startQuestion = pickRandom(new String[] {
                    "Pourquoi dites-vous que ",
                    "Pourquoi pensez-vous que ",
                    "Êtes-vous sûr que ",
            });
            final String answer = startQuestion + firstToSecondPerson(matcher.group(1)) + " ?";
            dataList.add(new Data(
                    answer,
                    true, dataList.size()+1)
            );
            notifyObservers();
            return;
        }
        pattern = Pattern.compile("(.*)\\?", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(normalizedText.getMessage());
        if (matcher.matches()) {
            final String startQuestion = pickRandom(new String[] {
                    "Je vous renvoie la question ",
                    "Ici, c'est moi qui pose les\n" +  "questions. ",
            });
            dataList.add(new Data((startQuestion), true, dataList.size()+1));
            notifyObservers();
            return;
        }
        // Nothing clever to say, answer randomly
        if (random.nextBoolean()) {
            final String answer = "Il faut beau aujourd'hui, vous ne trouvez pas ?";
            dataList.add(new Data(answer, true, dataList.size()+1));
            notifyObservers();
            return;
        }
        if (random.nextBoolean()) {
            final String answer = "Je ne comprends pas.";
            dataList.add(new Data(answer, true, dataList.size()+1));
            notifyObservers();
            return;
        }
        if (random.nextBoolean()) {
            final String answer = "Hmmm, hmm ...";
            dataList.add(new Data(answer, true, dataList.size()+1));
            notifyObservers();
            return;
        }
        // Default answer
        if (name != null) {
            final String answer = "Qu'est-ce qui vous fait dire cela, " + name + " ?";
            dataList.add(new Data(answer, true, dataList.size()+1));
            notifyObservers();
        } else {
            final String answer = "Qu'est-ce qui vous fait dire cela ?";
            dataList.add(new Data(
                    answer,
                    true, dataList.size()+1)
            );
            notifyObservers();
        }
    }

    /**
     * Information about conjugation of a verb.
     */
    public static class Verb {
        private final String firstSingular;
        private final String secondPlural;

        public String getFirstSingular() {
            return firstSingular;
        }

        public String getSecondPlural() {
            return secondPlural;
        }

        Verb(final String firstSingular, final String secondPlural) {
            this.firstSingular = firstSingular;
            this.secondPlural = secondPlural;
        }
    }

    /**
     * List of 3rd group verbs and their correspondance from 1st person signular
     * (Je) to 2nd person plural (Vous).
     */
    protected static final List<Verb> VERBS = Arrays.asList(
            new Verb("suis", "êtes"),
            new Verb("vais", "allez"),
            new Verb("peux", "pouvez"),
            new Verb("dois", "devez"),
            new Verb("dis", "dites"),
            new Verb("ai", "avez"),
            new Verb("fais", "faites"),
            new Verb("sais", "savez"),
            new Verb("dois", "devez"));

    /**
     * Turn a 1st-person sentence (Je ...) into a plural 2nd person (Vous ...).
     * The result is not capitalized to allow forming a new sentence.
     *
     *
     *
     * @param text
     * @return The 2nd-person sentence.
     */
    public String firstToSecondPerson(final String text) {
        String processedText = text
                .replaceAll("[Jj]e ([a-z]*)e ", "vous $1ez ");
        for (Verb v : VERBS) {
            processedText = processedText.replaceAll(
                    "[Jj]e " + v.getFirstSingular(),
                    "vous " + v.getSecondPlural());
        }
        processedText = processedText
                .replaceAll("[Jj]e ([a-z]*)s ", "vous $1ssez ")
                .replace("mon ", "votre ")
                .replace("ma ", "votre ")
                .replace("mes ", "vos ")
                .replace("moi", "vous");
        return processedText;
    }

    /** Pick an element randomly in the array. */
    public <T> T pickRandom(final T[] array) {
        return array[random.nextInt(array.length)];
    }
}
