package fr.univ_lyon1.info.m1.elizagpt.model;

import fr.univ_lyon1.info.m1.elizagpt.model.Filter.Filter;
import fr.univ_lyon1.info.m1.elizagpt.model.Filter.FilterSubstring;
import fr.univ_lyon1.info.m1.elizagpt.model.Message.MessageList;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests for FilterSubstring.
 */
public class FilterSubstringTest {
    private Filter substring = new FilterSubstring();

    /**
     * Testing the filter substring
     * we add message and search for "Je"
     * we should not have "Bonjour" and "Salut"
     * but the two others message so size 2.
     */
    @Test
    void testDoFilterSubstring() {
        MessageList messageList = new MessageList();
        messageList.add("Salut", false);
        messageList.add("Je ne comprends pas", true);
        messageList.add("Je sais que tu ne comprends pas", false);
        messageList.add("Ceci est une réponse avec je mais en minuscule", true);

        substring.doFilter("Je", messageList);
        assertThat(messageList.getSize(), is(2));
    }

    /**
     * Testing the filter substring
     * we add message and search for ""
     * we should have everything so size 4.
     */
    @Test
    void testDoFilterSubstringNoFilter() {
        MessageList messageList = new MessageList();
        messageList.add("Salut", false);
        messageList.add("Je ne comprends pas", true);
        messageList.add("Je sais que tu ne comprends pas", false);

        substring.doFilter("", messageList);
        assertThat(messageList.getSize(), is(4));
    }

    /**
     * Testing the filter substring
     * we add message and search but with no search
     * we should have nothing so size 0.
     */
    @Test
    void testDoFilterSubstringNull() {
        MessageList messageList = new MessageList();
        messageList.add("Salut", false);
        messageList.add("Je ne comprends pas", true);
        messageList.add("Je sais que tu ne comprends pas", false);

        substring.doFilter(null, messageList);
        assertThat(messageList.getSize(), is(0));
    }

    /**
     * Testing the filter substring
     * the toString should return the
     * name of the Class.
     */
    @Test
    void testToString() {
        assertThat(substring.toString(), is("Substring"));
    }
}
