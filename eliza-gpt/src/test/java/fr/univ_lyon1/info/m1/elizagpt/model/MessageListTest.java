package fr.univ_lyon1.info.m1.elizagpt.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests for MessageList.
 */
public class MessageListTest {
    private MessageList messageList = new MessageList();

    @Test
    void testMessageListConstructor() {
        assertThat(messageList.pullLastMessage().getMessage(), is("Bonjour"));
        assertThat(messageList.pullLastMessage().getIsFromEliza(), is(true));
    }

    @Test
    void testMessageListConstructorCopy() {
        messageList.add("Salut mec", false);
        messageList.add("Pas de réponse", true);
        MessageList messageListTest = new MessageList(messageList);

        assertThat(messageListTest.getSize(), is(3));
    }

    @Test
    void testMessageListPull() {
        messageList.add("Message 1", true);
        messageList.add("Message 2", false);
        messageList.add("Message 3", true);

        List<Message> allMessages = messageList.pullAllMessage();

        assertThat(allMessages.size(), is(4));

        assertThat(allMessages.get(0).getMessage(), is("Bonjour"));
        assertThat(allMessages.get(1).getMessage(), is("Message 1"));
        assertThat(allMessages.get(2).getMessage(), is("Message 2"));
        assertThat(allMessages.get(3).getMessage(), is("Message 3"));
    }

    @Test
    void testMessageListRemoveAll() {
        messageList.add("Message 1", true);
        messageList.add("Message 2", false);
        messageList.add("Message 3", true);

        messageList.removeAll();

        assertThat(messageList.getSize(), is(0));
    }

    @Test
    void testMessageListRemove() {
        messageList.add("Message 1", false);

        messageList.remove(1);
        assertThat(messageList.getSize(), is(1));
    }

    @Test
    void testMessageListGet() {
        assertThat(messageList.get(0).getMessage(),  is("Bonjour"));
    }
}
