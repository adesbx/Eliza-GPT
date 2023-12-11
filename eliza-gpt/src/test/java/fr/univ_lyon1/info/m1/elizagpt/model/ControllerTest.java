package fr.univ_lyon1.info.m1.elizagpt.model;


import fr.univ_lyon1.info.m1.elizagpt.controller.Controller;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests for Controller.
 */
public class ControllerTest {
    private MessageList list = new MessageList();
    private Controller ctrl = new Controller(new MessageProcessor(list), list);

    @Test
    void testControllerConstructor() {
        assertThat(ctrl.getMessageList().pullLastMessage().getMessage(), is("Bonjour"));
    }

    @Test
    void testControllerTreatMessage() {
        ctrl.treatMessage("Je m'appelle Bob");
        assertThat(ctrl.getMessageList().pullLastMessage().getMessage(), is("Bonjour Bob."));
    }

    @Test
    void testControllerRemoveMessage() {
        ctrl.removeMessage(1);
        assertThat(ctrl.getMessageList().pullAllMessage().size(), is(0));
    }

    @Test
    void testControllerDoFilter() {
        ctrl.filterMessage("C.*", new FilterRegex());
        assertThat(ctrl.getMessageList().pullAllMessage().size(), is(0));

        ctrl.filterMessage("B.*", new FilterRegex());
        assertThat(ctrl.getMessageList().pullAllMessage().size(), is(1));
    }

    @Test
    void testControllerUndoFilter() {
        ctrl.filterMessage("C.*", new FilterRegex());
        assertThat(ctrl.getMessageList().pullAllMessage().size(), is(0));

        ctrl.undoFilter();
        assertThat(ctrl.getMessageList().pullAllMessage().size(), is(1));
    }
}
