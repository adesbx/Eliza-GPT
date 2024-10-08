package fr.univ_lyon1.info.m1.elizagpt.model.Message;

import fr.univ_lyon1.info.m1.elizagpt.view.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class for a message, contains the content and whether it is sent by eliza or not.
 */
public class MessageList extends Observable {
    private List<Message> messageList = new ArrayList<>();
    private Integer compteur = 0;

    /**
     * Constructor of the class.
     */
    public MessageList() {
        this.add("Bonjour", true);
    }

    /**
     * Constructor by copy of the class.
     * @param newMessageList the MessageList to copy
     */
    public MessageList(final MessageList newMessageList) {
        for (Message msg : newMessageList.messageList) {
            messageList.add(new Message(msg.getMessage(), msg.getIsFromEliza(), msg.getId()));
        }
        for (Observer obs : newMessageList.getObserver()) {
            this.addObserver(obs);
        }
        compteur = newMessageList.compteur;
        //addObserver(newMessageList.);
    }

    /**
     * remove from the messageList per the Id.
     * @param id the id of the message to remove
     */
    public void remove(final int id) {
        Optional<Message> objFind = messageList.stream()
                .filter(objet -> objet.getId() == id).findFirst();
        objFind.ifPresent(obj -> {
            //System.out.println("Object to remove : " + obj.getMessage());
            messageList.remove(obj);
        });
        notifyObservers();
    }

    /**
     * remove all the message from the messageList .
     */
    public void removeAll() {
        messageList.clear();
    }

    /**
     * pull the lastResponse from the front.
     * @return Message
     */
    public Message pullLastMessage() {
        return messageList.get(messageList.size() - 1);
    }

    /**
     * pull all message list.
     * @return List Message
     */
    public List<Message> pullAllMessage() {
        return messageList;
    }

    /**
     * return the size.
     * @return int
     */
    public int getSize() {
        return messageList.size();
    }

    /**
     * get specific message per Id.
     * @param id
     * @return Message
     */
    public Message get(final int id) {
        return messageList.get(id);
    }

    /**
     * add a message to the list.
     * @param newMessage
     * @param isFromEliza
     */
    public void add(final String newMessage, final Boolean isFromEliza) {
        compteur++;
        messageList.add(new Message(newMessage, isFromEliza, compteur));
        //debug:
        //for (Message message : messageList) {
            //System.out.println(message.getMessage() + " - " + message.getId());
            //System.out.println("\n");
        //}
        //System.out.println("---------------------------\n");
        notifyObservers();
    }
}
