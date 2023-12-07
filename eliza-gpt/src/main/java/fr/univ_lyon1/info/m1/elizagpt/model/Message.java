package fr.univ_lyon1.info.m1.elizagpt.model;

/**
 * structure of a message with the content, the author and the id in the list.
 */
public class Message {
    private String message = null;
    private Boolean isFromEliza = null;
    private int id = 0;

    /**
     * constructeur de message.
     *
     * @param newMessage
     * @param newIsFromEliza
     */
    public Message(final String newMessage, final Boolean newIsFromEliza, final int newId) {
        message = newMessage;
        isFromEliza = newIsFromEliza;
        id = newId;
    }

    public String getMessage() {
        return message;
    }

    public boolean getIsFromEliza() {
        return isFromEliza;
    }

    public int getId() {
        return id;
    }
}
