package fr.univ_lyon1.info.m1.elizagpt.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests for MessageProcessor.
 */
public class MessageProcessorTest {
    @Test
    void testFirstToSecondPerson() {
        // Given
        Verb v = new Verb();

        // Then
        assertThat(v.firstToSecondPerson("Je pense à mon chien."),
                is("vous pensez à votre chien."));

        assertThat(v.firstToSecondPerson("Je suis heureux."),
                is("vous êtes heureux."));

        assertThat(v.firstToSecondPerson("Je dis bonjour."),
                is("vous dites bonjour."));

        assertThat(v.firstToSecondPerson("Je vais à la mer."),
                is("vous allez à la mer."));

        assertThat(v.firstToSecondPerson("Je finis mon travail."),
                is("vous finissez votre travail."));
    }
}
