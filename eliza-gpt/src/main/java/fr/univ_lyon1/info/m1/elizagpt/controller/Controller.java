package fr.univ_lyon1.info.m1.elizagpt.controller;

import fr.univ_lyon1.info.m1.elizagpt.model.Message;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageList;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;

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
    public void treatMessage(final Message text) {
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
}
