package fr.univ_lyon1.info.m1.elizagpt.controller;

import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
/**
 * Main class of the Controller (GUI) of the application.
 */
public class Controller {
    private MessageProcessor processor = null;
    /**
     * constructeur du controleur avec l'instance de la vue.
     * @param newProcessor
     */
    public Controller(final MessageProcessor newProcessor) {
        processor = newProcessor;
    }

    /**
     * fonction qui appelle le model pour répondre au message de l'utilisateur.
     * @param text
     */
    public void treatMessage(final String text) {
        String normalizedText = processor.normalize(text);
        processor.easyAnswer(normalizedText);
    }
}
