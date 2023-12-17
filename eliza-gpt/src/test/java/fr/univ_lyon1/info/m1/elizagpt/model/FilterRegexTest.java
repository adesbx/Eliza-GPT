package fr.univ_lyon1.info.m1.elizagpt.model;

import fr.univ_lyon1.info.m1.elizagpt.model.Filter.Filter;
import fr.univ_lyon1.info.m1.elizagpt.model.Filter.FilterRegex;
import fr.univ_lyon1.info.m1.elizagpt.model.Message.MessageList;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests for FilterRegex.
 */
public class FilterRegexTest {
    private Filter regex = new FilterRegex();

    /**
     * Testing the filter regex
     * we add message and search for "Je.*"
     * we should not have "Bonjour" and "Salut"
     * but the two others message so size 2.
     */
    @Test
    void testDoFilterRegex() {
        MessageList messageList = new MessageList();
        messageList.add("Salut", false);
        messageList.add("Je ne comprends pas", true);
        messageList.add("Je sais que tu ne comprends pas", false);

        regex.doFilter("Je.*", messageList);
        assertThat(messageList.getSize(), is(2));
    }

    /**
     * Testing the filter regex
     * we add message and search for ""
     * we should have everything so size 4.
     */
    @Test
    void testDoFilterRegexNoFilter() {
        MessageList messageList = new MessageList();
        messageList.add("Salut", false);
        messageList.add("Je ne comprends pas", true);
        messageList.add("Je sais que tu ne comprends pas", false);

        regex.doFilter("", messageList);
        assertThat(messageList.getSize(), is(4));
    }

    /**
     * Testing the filter regex
     * we add message and search but with no search
     * we should have nothing so size 0.
     */
    @Test
    void testDoFilterRegexNull() {
        MessageList messageList = new MessageList();
        messageList.add("Salut", false);
        messageList.add("Je ne comprends pas", true);
        messageList.add("Je sais que tu ne comprends pas", false);

        regex.doFilter(null, messageList);
        assertThat(messageList.getSize(), is(0));
    }

    /**
     * Testing the filter regex
     * the toString should return the
     * name of the Class.
     */
    @Test
    void testToString() {
        assertThat(regex.toString(), is("Regex"));
    }
}
