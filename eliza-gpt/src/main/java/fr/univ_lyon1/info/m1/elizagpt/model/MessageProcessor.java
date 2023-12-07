package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.List;
import java.util.ArrayList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Logic to process a message (and probably reply to it).
 */

public class MessageProcessor {
    private MessageList messageList = null;
    private DataApplication<String> dataApplication = new DataApplication();

    private MessagePattern messagePattern = new MessagePattern(dataApplication);

    /**
     * Constructor of MessageProcessor.
     *
     * @param msgList
     */
    public MessageProcessor(final MessageList msgList) {
        messageList = msgList;
    }

    /**
     * Normlize the text: remove extra spaces, add a final dot if missing.
     *
     * @param text
     * @return normalized text.
     */
    public Message normalize(final String text) {
        return new Message(text.replaceAll("\\s+", " ")
                .replaceAll("^\\s+", "")
                .replaceAll("\\s+$", "")
                .replaceAll("[^\\.!?:]$", "$0."), null, -1);
    }

    private void searchText(final Message text) {

        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(text.getMessage(), Pattern.CASE_INSENSITIVE);

        List<Integer> toDelete = new ArrayList<>();
        for (Message message : messageList.pullAllMessage()) {
            matcher = pattern.matcher(text.getMessage());
            if (!matcher.matches()) {
                // Can delete it right now, we're iterating over the list.
                toDelete.add(message.getId());
            }
        }

    }

    /**
     * remplace variable in Answer with Data or other treatment.
     * @param answer
     * @return
     */
    private String fillWithDataAnswer(final String answer) {
        for (DataType dataType : DataType.values()) {
            String toReplace = "$".concat(dataType.name());
            if (answer.contains(toReplace)) {
                if (dataApplication.get(dataType) != null) {
                    return answer.replace(toReplace, dataApplication.get(dataType));
                } else {
                    return answer.replace(toReplace, "");
                }
            }
        }
        return answer;
    }

    /**
     * Traite le message envoyé par l'utilisateur.
     *
     * @param normalizedText
     */
    public void easyAnswer(final Message normalizedText) {

        messageList.add(normalizedText.getMessage(), false);

        String unfilledAnswer = messagePattern.getAnswer(normalizedText.getMessage());

        String cleanAnswer = fillWithDataAnswer(unfilledAnswer);

        messageList.add(cleanAnswer, true);
    }
}
