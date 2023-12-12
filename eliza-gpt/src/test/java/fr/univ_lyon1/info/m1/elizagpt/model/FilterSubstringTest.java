package fr.univ_lyon1.info.m1.elizagpt.model;

import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests for FilterSubstring.
 */
public class FilterSubstringTest {
    private Filter substring = new FilterSubstring();

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

    @Test
    void testDoFilterSubstringNoFilter() {
        MessageList messageList = new MessageList();
        messageList.add("Salut", false);
        messageList.add("Je ne comprends pas", true);
        messageList.add("Je sais que tu ne comprends pas", false);

        substring.doFilter("", messageList);
        assertThat(messageList.getSize(), is(4));
    }

    @Test
    void testDoFilterSubstringNull() {
        MessageList messageList = new MessageList();
        messageList.add("Salut", false);
        messageList.add("Je ne comprends pas", true);
        messageList.add("Je sais que tu ne comprends pas", false);

        substring.doFilter(null, messageList);
        assertThat(messageList.getSize(), is(0));
    }

    @Test
    void testToString() {
        assertThat(substring.toString(), is("Substring"));
    }
}
