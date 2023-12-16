package fr.univ_lyon1.info.m1.elizagpt.model;

import fr.univ_lyon1.info.m1.elizagpt.model.Filter.Filter;
import fr.univ_lyon1.info.m1.elizagpt.model.Filter.FilterCompleteWord;
import fr.univ_lyon1.info.m1.elizagpt.model.Message.MessageList;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests for FilterCompleteWord.
 */
public class FilterCompleteWordTest {
    private Filter completeword = new FilterCompleteWord();

    @Test
    void testDoFilterCompleteWord() {
        MessageList messageList = new MessageList();
        messageList.add("Salut", false);
        messageList.add("Ceci est un beau jour", true);
        messageList.add("Oui un beau jour n'est ce pas ?", false);

        completeword.doFilter("jour", messageList);
        assertThat(messageList.getSize(), is(2));
    }

    @Test
    void testDoFilterCompleteWordNoFilter() {
        MessageList messageList = new MessageList();
        messageList.add("Salut", false);
        messageList.add("Je ne comprends pas", true);
        messageList.add("Je sais que tu ne comprends pas", false);

        completeword.doFilter("", messageList);
        assertThat(messageList.getSize(), is(4));
    }

    @Test
    void testDoFilterCompleteWordNull() {
        MessageList messageList = new MessageList();
        messageList.add("Salut", false);
        messageList.add("Je ne comprends pas", true);
        messageList.add("Je sais que tu ne comprends pas", false);

        completeword.doFilter(null, messageList);
        assertThat(messageList.getSize(), is(0));
    }

    @Test
    void testToString() {
        assertThat(completeword.toString(), is("CompleteWord"));
    }
}
