package fr.univ_lyon1.info.m1.elizagpt.model;

import fr.univ_lyon1.info.m1.elizagpt.model.Message.Message;
import fr.univ_lyon1.info.m1.elizagpt.model.Message.MessageList;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests for MessageList.
 */
public class MessageListTest {
    private MessageList messageList = new MessageList();

    /**
     * Testing the constructor, working if there is a message "Bonjour".
     */
    @Test
    void testMessageListConstructor() {
        assertThat(messageList.pullLastMessage().getMessage(), is("Bonjour"));
    }

    /**
     * Testing the constructor by copy,
     * working if there is the same size of
     * Message in both list.
     */
    @Test
    void testMessageListConstructorCopy() {
        messageList.add("Salut mec", false);
        messageList.add("Pas de réponse", true);
        MessageList messageListTest = new MessageList(messageList);

        assertThat(messageListTest.getSize(), is(messageList.getSize()));
    }

    /**
     * Testing the constructor by copy,
     * working if there is the same size of
     * Message in both list. Here we use a list
     * with no message.
     */
    @Test
    void testMessageListConstructorCopyNull() {
        messageList.remove(1);
        MessageList messageListTest = new MessageList(messageList);

        assertThat(messageListTest.getSize(), is(messageList.getSize()));
    }

    /**
     * Testing if the getIsFromEliza is working.
     */
    @Test
    void testMessageListIsElisa() {
        assertThat(messageList.pullLastMessage().getIsFromEliza(), is(true));
        messageList.add("Salut mec", false);
        assertThat(messageList.pullLastMessage().getIsFromEliza(), is(false));
    }

    /**
     * Testing the pullAllMessage, we add
     * 3 Message when we pull the size should be 4.
     */
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

    /**
     * Testing the pullAllMessage, with no message
     * when we pull the size should be 0.
     */
    @Test
    void testMessageListPullNull() {
        messageList.remove(1);
        List<Message> allMessages = messageList.pullAllMessage();

        assertThat(allMessages.size(), is(0));
    }

    /**
     * Testing the removeAll,
     * should remove all the message in the list
     * the size should be 0.
     */
    @Test
    void testMessageListRemoveAll() {
        messageList.add("Message 1", true);
        messageList.add("Message 2", false);
        messageList.add("Message 3", true);

        messageList.removeAll();

        assertThat(messageList.getSize(), is(0));
    }

    /**
     * Testing the removeAll,
     * should remove all the message in the list,
     * if we have not message size should be 0.
     */
    @Test
    void testMessageListRemoveAllNull() {
        messageList.remove(1);
        messageList.removeAll();

        assertThat(messageList.getSize(), is(0));
    }

    /**
     * Testing the remove one message,
     * should remove the message in the list,
     * size should be 0.
     */
    @Test
    void testMessageListRemove() {
        messageList.add("Message 1", false);

        messageList.remove(1);
        assertThat(messageList.getSize(), is(1));
    }

    /**
     * Testing the remove one message,
     * should remove the message in the list,
     * if the message doesn't exist should not create
     * error.
     */
    @Test
    void testMessageListRemoveNoExisting() {
        messageList.remove(1);
        messageList.remove(1);
        messageList.remove(-1);
        assertThat(messageList.getSize(), is(0));
    }

    /**
     * Testing the get list message.
     */
    @Test
    void testMessageListGet() {
        assertThat(messageList.get(0).getMessage(),  is("Bonjour"));
    }
}
