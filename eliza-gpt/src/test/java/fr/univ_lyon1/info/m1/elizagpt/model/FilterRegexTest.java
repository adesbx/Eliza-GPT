package fr.univ_lyon1.info.m1.elizagpt.model;

import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests for FilterRegex.
 */
public class FilterRegexTest {
    private Filter regex = new FilterRegex();

    @Test
    void testDoFilterRegex() {
        MessageList messageList = new MessageList();
        messageList.add("Salut", false);
        messageList.add("Je ne comprends pas", true);
        messageList.add("Je sais que tu ne comprends pas", false);

        regex.doFilter("Je.*", messageList);
        assertThat(messageList.getSize(), is(2));
    }

    @Test
    void testDoFilterRegexNoFilter() {
        MessageList messageList = new MessageList();
        messageList.add("Salut", false);
        messageList.add("Je ne comprends pas", true);
        messageList.add("Je sais que tu ne comprends pas", false);

        regex.doFilter("", messageList);
        assertThat(messageList.getSize(), is(4));
    }

    @Test
    void testDoFilterRegexNull() {
        MessageList messageList = new MessageList();
        messageList.add("Salut", false);
        messageList.add("Je ne comprends pas", true);
        messageList.add("Je sais que tu ne comprends pas", false);

        regex.doFilter(null, messageList);
        assertThat(messageList.getSize(), is(0));
    }

    @Test
    void testToString() {
        assertThat(regex.toString(), is("Regex"));
    }
}
