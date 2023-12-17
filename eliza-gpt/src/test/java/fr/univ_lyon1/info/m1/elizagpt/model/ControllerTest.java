package fr.univ_lyon1.info.m1.elizagpt.model;


import fr.univ_lyon1.info.m1.elizagpt.controller.Controller;
import fr.univ_lyon1.info.m1.elizagpt.model.Filter.FilterRegex;
import fr.univ_lyon1.info.m1.elizagpt.model.Message.MessageList;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests for Controller.
 */
public class ControllerTest {
    private MessageList list = new MessageList();
    private Controller ctrl = new Controller(new MessageProcessor(list), list);

    /**
     * Testing the constructor, working if there is a message "Bonjour".
     */
    @Test
    void testControllerConstructor() {
        assertThat(ctrl.getMessageList().pullLastMessage().getMessage(), is("Bonjour"));
    }

    /**
     * Testing the answer, by saying "Je m'appelle Bob"
     * we take the last message in our messageList
     * and see if the response is "Bonjour Bob".
     */
    @Test
    void testControllerTreatMessage() {
        ctrl.treatMessage("Je m'appelle Bob");
        assertThat(ctrl.getMessageList().pullLastMessage().getMessage(), is("Bonjour Bob."));
    }

    /**
     * Testing to remove Message
     * we remove the "Bonjour" and see if
     * the size is -1.
     */
    @Test
    void testControllerRemoveMessage() {
        ctrl.removeMessage(1);
        assertThat(ctrl.getMessageList().pullAllMessage().size(), is(0));
    }

    /**
     * Testing the filter, by using regex
     * if we search for a message with C
     * we should have nothing so size 0
     * but if we search B we should have
     * "Bonjour" so size 1.
     */
    @Test
    void testControllerDoFilter() {
        ctrl.filterMessage("C.*", new FilterRegex());
        assertThat(ctrl.getMessageList().pullAllMessage().size(), is(0));

        ctrl.filterMessage("B.*", new FilterRegex());
        assertThat(ctrl.getMessageList().pullAllMessage().size(), is(1));
    }

    /**
     * Testing the undo filter, by using regex
     * if we search for a message with C
     * we should have nothing so size 0
     * but if we undo we should have the
     * list with "Bonjour" so size 1.
     */
    @Test
    void testControllerUndoFilter() {
        ctrl.filterMessage("C.*", new FilterRegex());
        assertThat(ctrl.getMessageList().pullAllMessage().size(), is(0));

        ctrl.undoFilter();
        assertThat(ctrl.getMessageList().pullAllMessage().size(), is(1));
    }
}
