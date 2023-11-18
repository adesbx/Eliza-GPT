package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Classe pour un message, contient le contenue et s'il est envoyé par le robot ou non.
 */
public class MessageList extends Observable {
    private final List<Message> messageList = new ArrayList<>();
    private Integer compteur = 0;
    public MessageList() {
        this.add("Bonjour", true);
    }

    public void removeFromMessageList(final int id) {
        Optional<Message> objFind = messageList.stream().filter(objet -> objet.getId() == id).findFirst();
        objFind.ifPresent(obj -> {
            System.out.println("Object to remove : " + obj.getMessage());
            messageList.remove(obj);
        });
    }

    public Message lastResponse() {
        return messageList.get(messageList.size() - 1);
    }

    public int getSize() {
        return messageList.size();
    }

    public Message get(final int id) {
        return messageList.get(id);
    }

    public void add(final String newMessage, final Boolean newIsAnswer) {
        compteur++;
        messageList.add(new Message(newMessage, newIsAnswer, compteur));
        for (Message message : messageList) {
            System.out.println(message.getMessage() + " - " + message.getId());
            System.out.println("\n");
        }
        System.out.println("---------------------------\n");
        if(newIsAnswer) {
            notifyObservers();
        }
    }
}