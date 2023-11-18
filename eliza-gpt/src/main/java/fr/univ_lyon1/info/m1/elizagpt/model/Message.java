package fr.univ_lyon1.info.m1.elizagpt.model;

public class Message {
    private String message = null;
    private Boolean isAnswer = null;//changer nom encore fromElisa avec un type enumerate

    private int id = 0;

    /**
     * constructeur de message.
     *
     * @param newMessage
     * @param newIsAnswer
     */
    public Message(final String newMessage, final Boolean newIsAnswer, final int newHashCode) {
        message = newMessage;
        isAnswer = newIsAnswer;
        id = newHashCode;
    }

    public String getMessage() {
        return message;
    }

    public boolean getIsAnswer() {
        return isAnswer;
    }

    public int getId() {
        return id;
    }
}