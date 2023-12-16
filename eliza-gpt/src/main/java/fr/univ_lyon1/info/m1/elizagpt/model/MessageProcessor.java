package fr.univ_lyon1.info.m1.elizagpt.model;

import fr.univ_lyon1.info.m1.elizagpt.model.Data.DataApplication;
import fr.univ_lyon1.info.m1.elizagpt.model.Data.DataType;
import fr.univ_lyon1.info.m1.elizagpt.model.Filter.Filter;
import fr.univ_lyon1.info.m1.elizagpt.model.Filter.FilterCompleteWord;
import fr.univ_lyon1.info.m1.elizagpt.model.Filter.FilterRegex;
import fr.univ_lyon1.info.m1.elizagpt.model.Filter.FilterSubstring;
import fr.univ_lyon1.info.m1.elizagpt.model.Message.Message;
import fr.univ_lyon1.info.m1.elizagpt.model.Message.MessageList;
import fr.univ_lyon1.info.m1.elizagpt.model.Message.MessagePattern;

import java.util.ArrayList;
import java.util.List;

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
     * @param text the string to normalize
     * @return Message normalized text.
     */
    public Message normalize(final String text) {
        return new Message(text.replaceAll("\\s+", " ")
                .replaceAll("^\\s+", "")
                .replaceAll("\\s+$", "")
                .replaceAll("[^\\.!?:]$", "$0."), null, -1);
    }

    /**
     * remplace variable in Answer with Data or other treatment.
     *
     * @param answer the answer with no replacement
     * @return String the answer with replacement
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
     * Treat the message send by the user and respond with the
     * good answers.
     *
     * @param text the text send by the user
     */
    public void easyAnswer(final String text) {
        Message normalizedText = normalize(text);

        messageList.add(normalizedText.getMessage(), false);

        String unfilledAnswer = messagePattern.getAnswer(normalizedText.getMessage());

        String cleanAnswer = fillWithDataAnswer(unfilledAnswer);

        messageList.add(cleanAnswer, true);

    }

    /**
     * Apply the right way to filter.
     * @param filter the filter to use
     * @param searchText the text you want to search
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
     * get a List of Filter.
     */
    public List<Filter> getFilterList() {
        List<Filter> list = new ArrayList<>();
        list.add(new FilterRegex());
        list.add(new FilterSubstring());
        list.add(new FilterCompleteWord());

        return list;
    }


}
