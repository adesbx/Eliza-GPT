package fr.univ_lyon1.info.m1.elizagpt.controller;

import fr.univ_lyon1.info.m1.elizagpt.model.Message.MessageList;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
import fr.univ_lyon1.info.m1.elizagpt.model.Filter.Filter;

import java.util.List;

/**
 * Main class of the Controller (GUI) of the application.
 */
public class Controller {
    private MessageProcessor processor = null;
    private MessageList messageList = null;

    /**
     * Constructor of controller with a MessageList.
     *
     * @param newProcessor
     * @param newMessageList the messageList to instance with
     */
    public Controller(final MessageProcessor newProcessor, final MessageList newMessageList) {
        processor = newProcessor;
        messageList = newMessageList;
    }

    /**
     * Function that call the model to respond to the user.
     * @param text
     */
    public void treatMessage(final String text) {
        processor.easyAnswer(text);
    }

    /**
     * removeMessage from the messageList.
     * @param id id of the message to remove
     */
    public void removeMessage(final int id) {
        //System.out.println("id du removeMessage" + id);
        messageList.remove(id);
    }

    /**
     * filter All messages.
     * @param searchText the search you want to search
     * @param filter the filter you are going to use
     */
    public void filterMessage(final String searchText, final Filter filter) {
        processor.doFilterAnswer(searchText, filter);
    }

    /**
     * Undo a filter.
     */
    public void undoFilter() {
        processor.undoFilterMessageList();
    }

    /**
     * Get the messagelist you want.
     * @return MessageList
     */
    public MessageList getMessageList() {
        return  processor.getMessageList();
    }

    /**
     * get a List of Filter.
     * @return list of filter
     */
    public List<Filter> getFilterList() {
        return processor.getFilterList();
    }
}
