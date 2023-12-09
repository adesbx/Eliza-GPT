package fr.univ_lyon1.info.m1.elizagpt.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.util.Map;
import java.util.HashMap;
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

    private MessageList filterMessageList = null;

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

    /**
     * Apply the right way to filter.
     */
    public void doFilterAnswer(final String searchText, final Filter filter) {
        filterMessageList = new MessageList(messageList);
        filter.doFilter(searchText, filterMessageList);
        filterMessageList.notifyObservers();
    }

    /**
     * Undo the current filter.
     */
    public void undoFilterMessageList() {
        filterMessageList = null;
        messageList.notifyObservers();
    }

    /**
     * Get the messagelist you whant to see(with filter or not).
     */
    public MessageList getMessageList() {
        if (filterMessageList != null) {
            return filterMessageList;
        } else {
            return messageList;
        }
    }

    /**
     * get a ObservableList of Filter.
     */
    public ObservableList<Filter> getFilterList() {
        Filter regex = new FilterRegex();
        Filter substring = new FilterSubstring();

        ObservableList<Filter> list
                = FXCollections.observableArrayList(regex, substring);

        return list;
    }

    
}
