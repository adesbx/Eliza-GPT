package fr.univ_lyon1.info.m1.elizagpt.model;

//import fr.univ_lyon1.info.m1.elizagpt.model.Dao.Verb;
import fr.univ_lyon1.info.m1.elizagpt.model.Filter.FilterCompleteWord;
import fr.univ_lyon1.info.m1.elizagpt.model.Filter.FilterRegex;
import fr.univ_lyon1.info.m1.elizagpt.model.Filter.FilterSubstring;
import fr.univ_lyon1.info.m1.elizagpt.model.Message.Message;
import fr.univ_lyon1.info.m1.elizagpt.model.Message.MessageList;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Tests for MessageProcessor.
 */
public class MessageProcessorTest {
    private final MessageProcessor processor = new MessageProcessor(new MessageList());

//    @Test
//    void testFirstToSecondPerson() {
//        // Given
//        Verb v = new Verb();
//
//        // Then
//        assertThat(v.firstToSecondPerson("Je pense à mon chien."),
//                is("vous pensez à votre chien."));
//
//        assertThat(v.firstToSecondPerson("Je suis heureux."),
//                is("vous êtes heureux."));
//
//        assertThat(v.firstToSecondPerson("Je dis bonjour."),
//                is("vous dites bonjour."));
//
//        assertThat(v.firstToSecondPerson("Je vais à la mer."),
//                is("vous allez à la mer."));
//
//        assertThat(v.firstToSecondPerson("Je finis mon travail."),
//                is("vous finissez votre travail."));
//    }

    /**
     * Testing the answer, by saying "Qui est le plus beau ?"
     * we take the last message in our messageList
     * and see if the response is "Le plus beau est bien sûr votre enseignant de MIF01 !".
     */
    @Test
    void testMessageProcessorEasyAnswer() {
        Message msg = new Message("Qui est le plus beau ?", false, 1);
        processor.easyAnswer(msg.getMessage());

        assertThat(processor.getMessageList().pullLastMessage().getMessage(),
                is("Le plus beau est bien sûr votre enseignant de MIF01 !"));
    }

    /**
     * Testing the filter, by using substring
     * if we search for a message with "Bonjour"
     * we should have so the last Message in our list Message
     * to be "Bonjour".
     */
    @Test
    void testMessageProcessorDoFilterSubstring() {
        processor.doFilterAnswer("Bonjour", new FilterSubstring());

        assertThat(processor.getMessageList().pullLastMessage().getMessage(), is("Bonjour"));
    }

    /**
     * Testing the filter, by using regex
     * if we search for a message with "B.*"
     * we should have so the last Message in our list Message
     * to be "Bonjour".
     */
    @Test
    void testMessageProcessorDoFilterRegex() {
        processor.doFilterAnswer("B.*", new FilterRegex());

        assertThat(processor.getMessageList().pullLastMessage().getMessage(), is("Bonjour"));
    }

    /**
     * Testing the filter, by using completeword
     * if we search for a message with "Bonjour"
     * we should have so the last Message in our list Message
     * to be "Bonjour".
     */
    @Test
    void testMessageProcessorDoFilterCompleteWord() {
        processor.doFilterAnswer("Bonjour", new FilterCompleteWord());

        assertThat(processor.getMessageList().pullLastMessage().getMessage(), is("Bonjour"));
    }

    /**
     * Testing the undo filter, by using regex
     * we first add a Message to our list
     * if we search for a message with "Bon.*"
     * we should have the last Message of our
     * List Message to be "Bonjour"
     * but if we undo we should have the
     * last Message being the one we add at first.
     */
    @Test
    void testMessageProcessorUndoFilter() {
        Message msg = new Message("Qui est le plus beau ?", false, 1);
        processor.easyAnswer(msg.getMessage());

        processor.doFilterAnswer("Bon.*", new FilterRegex());

        assertThat(processor.getMessageList().pullLastMessage().getMessage(), is("Bonjour"));

        processor.undoFilterMessageList();

        assertThat(processor.getMessageList().pullLastMessage().getMessage(),
                is("Le plus beau est bien sûr votre enseignant de MIF01 !"));
    }

    /**
     * Testing the undo filter, by using regex
     * if we search for a message with nothing
     * we should have size of 0
     * but if we undo we should have the
     * size of 1.
     */
    @Test
    void testMessageProcessorUndoFilterNullSearch() {
        processor.doFilterAnswer(null, new FilterRegex());

        assertThat(processor.getMessageList().getSize(), is(0));

        processor.undoFilterMessageList();

        assertThat(processor.getMessageList().getSize(), is(1));
    }

    /**
     * Testing the dataApplication,
     * if we search say "Je m'appelle x"
     * we should stock in our dataApplication the name
     * if so after eliza should respond with
     * "Bonjour x".
     */
    @Test
    void testMessageProcessorDataApplicationName() {
        Message msg = new Message("Je m'appelle Bob", false, 1);
        processor.easyAnswer(msg.getMessage());

        assertThat(processor.getMessageList().pullLastMessage().getMessage(), is("Bonjour Bob."));
    }

    /**
     * Testing the randomMessage,
     * if we say something that hasn't been coded
     * then the answer is random.
     */
    @Test
    void testMessageProcessorRandomResponse() {
        Message msg = new Message("Greg", false, 1);
        processor.easyAnswer(msg.getMessage());
        String[] string = new String[]{"Il fait beau aujourd'hui, vous ne trouvez pas ?",
                "Je ne comprends pas.",
                "Hmmm, hmm ...",
                "Qu'est-ce qui vous fait dire cela ?",
                "Il faut beau aujourd'hui, vous ne trouvez pas, Greg ?",
                "Je ne comprends pas. Greg",
                "Hmmm, hmm ... Greg",
                "Qu'est-ce qui vous fait dire cela Greg ?"
        };
        assertThat(Arrays.stream(string).anyMatch(
                str -> str.equals(processor.getMessageList().pullLastMessage().getMessage())),
                is(true));
    }

    /**
     * Testing the choiceAnswer,
     * if we say something that can have two answers
     * we check that we have the right answer at the right time.
     */
    @Test
    void testMessageProcessorChoiceAnswer() {
        Message msg = new Message("Quel est mon nom ?", false, 1);
        processor.easyAnswer(msg.getMessage());

        assertThat(processor.getMessageList().pullLastMessage().getMessage(),
                is("Je ne connais pas votre nom."));

        msg = new Message("Je m'appelle Bob", false, 3);
        processor.easyAnswer(msg.getMessage());

        msg = new Message("Quel est mon nom ?", false, 5);
        processor.easyAnswer(msg.getMessage());

        assertThat(processor.getMessageList().pullLastMessage().getMessage(),
                is("Votre nom est Bob."));
    }
}
