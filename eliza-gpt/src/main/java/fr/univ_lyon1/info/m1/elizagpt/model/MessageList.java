package fr.univ_lyon1.info.m1.elizagpt.model;

import fr.univ_lyon1.info.m1.elizagpt.view.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Classe pour un message, contient le contenue et s'il est envoyé par le robot ou non.
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
     * @param id
     */
    public void remove(final int id) {
        Optional<Message> objFind = messageList.stream()
                .filter(objet -> objet.getId() == id).findFirst();
        objFind.ifPresent(obj -> {
            System.out.println("Object to remove : " + obj.getMessage());
            messageList.remove(obj);
        });
    }

    /**
     * remove all the message from the messageList .
     */
    public void removeAll() {
        messageList.clear();
    }

    /**
     * pull the lastResponse from the front.
     * @return message
     */
    public Message pullLastMessage() {
        return messageList.get(messageList.size() - 1);
    }

    /**
     * pull all message list.
     * @return messageList
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
        for (Message message : messageList) {
            System.out.println(message.getMessage() + " - " + message.getId());
            System.out.println("\n");
        }
        System.out.println("---------------------------\n");
        notifyObservers();
    }
}
