package fr.univ_lyon1.info.m1.elizagpt.model.Message;

/**
 * structure of a message with the content, the author and the id in the list.
 */
public class Message {
    private String message = null;
    private Boolean isFromEliza = null;
    private int id = 0;

    /**
     * Constructor for Message.
     *
     * @param newMessage the message
     * @param newIsFromEliza if the message is from eliza
     */
    public Message(final String newMessage, final Boolean newIsFromEliza, final int newId) {
        message = newMessage;
        isFromEliza = newIsFromEliza;
        id = newId;
    }

    /**
     * Getter for Message.
     *
     * @return String the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Getter for boolean isFromEliza.
     *
     * @return boolean
     */
    public boolean getIsFromEliza() {
        return isFromEliza;
    }

    /**
     * Getter for ID.
     *
     * @return int the id of our message
     */
    public int getId() {
        return id;
    }
}
