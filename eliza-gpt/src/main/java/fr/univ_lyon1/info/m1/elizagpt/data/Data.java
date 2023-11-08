package fr.univ_lyon1.info.m1.elizagpt.data;

/**
 * Classe pour un message, contient le contenue et s'il est envoyé par le robot ou non.
 */

public class Data {
    private String message = null;
    private Boolean isAnswer = null;

    private int hashCode = 0;

    /**
     * constructeur de data.
     * @param newMessage
     * @param newIsAnswer
     */
    public Data(final String newMessage, final Boolean newIsAnswer, final int newHashCode) {
        message = newMessage;
        isAnswer = newIsAnswer;
        hashCode = newHashCode;
    }

    public String getMessage() {
        return message;
    }

    public boolean getIsAnswer() {
        return isAnswer;
    }

    public int getHashCode() {
        return hashCode;
    }
}
