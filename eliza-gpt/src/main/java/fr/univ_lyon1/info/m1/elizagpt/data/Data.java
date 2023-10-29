package fr.univ_lyon1.info.m1.elizagpt.data;

/**
 * Classe pour un message, contient le contenue et s'il est envoyé par le robot ou non.
 */

public class Data {
    private String message = null;
    private Boolean isAnswer = null;

    /**
     * constructeur de data.
     * @param newMessage
     * @param newIsAnswer
     */
    public Data(final String newMessage, final Boolean newIsAnswer) {
        message = newMessage;
        isAnswer = newIsAnswer;
    }

    public String getMessage() {
        return message;
    }

    public boolean getIsAnswer() {
        return isAnswer;
    }
}
