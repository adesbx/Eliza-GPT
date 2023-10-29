package fr.univ_lyon1.info.m1.elizagpt.controller;


import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Main class of the Controller (GUI) of the application.
 */
public class Controller {

    private MessageProcessor processor = null;

    /**
     * constructeur du controleur avec l'instance de la vue
     * @param
     */
    public Controller(MessageProcessor processor_){
        processor = processor_;
    }

    public void treatMessage(String text) {
        String normalizedText = processor.normalize(text);
        processor.easyAnswer(normalizedText);
    }
}
