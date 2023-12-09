package fr.univ_lyon1.info.m1.elizagpt.controller;

import fr.univ_lyon1.info.m1.elizagpt.model.Message;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageList;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
import fr.univ_lyon1.info.m1.elizagpt.model.Filter;

import java.util.List;

/**
 * Main class of the Controller (GUI) of the application.
 */
public class Controller {
    private MessageProcessor processor = null;
    private MessageList messageList = null;

    /**
     * constructeur du controleur avec l'instance de la vue.
     *
     * @param newProcessor
     */
    public Controller(final MessageProcessor newProcessor, final MessageList newMessageList) {
        processor = newProcessor;
        messageList = newMessageList;
    }

    /**
     * fonction qui appelle le model pour répondre au message de l'utilisateur.
     *
     * @param text
     */
    public void treatMessage(final String text) {
        Message normalizedText = processor.normalize(text);
        processor.easyAnswer(normalizedText);
    }

    /**
     * removeMessage.
     * @param id
     */
    public void removeMessage(final int id) {
        System.out.println("id du removeMessage" + id);
        messageList.remove(id);
    }

    /**
     * filter All message.
     * @param searchText
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

    public MessageList getMessageList() {
        return  processor.getMessageList();
    }

    /**
     * get a List of Filter.
     */
    public List<Filter> getFilterList() {
        return processor.getFilterList();
    }
}
