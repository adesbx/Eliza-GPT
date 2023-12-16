package fr.univ_lyon1.info.m1.elizagpt.model;

//import fr.univ_lyon1.info.m1.elizagpt.model.Dao.Verb;
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

    @Test
    void testMessageProcessorEasyAnswer() {
        Message msg = new Message("Qui est le plus beau ?", false, 1);
        Message newMsg = processor.normalize(msg.getMessage());
        processor.easyAnswer(newMsg);

        assertThat(processor.getMessageList().pullLastMessage().getMessage(),
                is("Le plus beau est bien sûr votre enseignant de MIF01 !"));
    }

    @Test
    void testMessageProcessorDoFilterSubstring() {
        processor.doFilterAnswer("Bonjour", new FilterSubstring());

        assertThat(processor.getMessageList().pullLastMessage().getMessage(), is("Bonjour"));
    }

    @Test
    void testMessageProcessorDoFilterRegex() {
        processor.doFilterAnswer("B.*", new FilterRegex());

        assertThat(processor.getMessageList().pullLastMessage().getMessage(), is("Bonjour"));
    }

    @Test
    void testMessageProcessorDoFilterCompleteWord() {
        processor.doFilterAnswer("Bonjour", new FilterCompleteWord());

        assertThat(processor.getMessageList().pullLastMessage().getMessage(), is("Bonjour"));
    }

    @Test
    void testMessageProcessorUndoFilter() {
        Message msg = new Message("Qui est le plus beau ?", false, 1);
        Message newMsg = processor.normalize(msg.getMessage());
        processor.easyAnswer(newMsg);

        processor.doFilterAnswer("Bon.*", new FilterRegex());

        assertThat(processor.getMessageList().pullLastMessage().getMessage(), is("Bonjour"));

        processor.undoFilterMessageList();

        assertThat(processor.getMessageList().pullLastMessage().getMessage(),
                is("Le plus beau est bien sûr votre enseignant de MIF01 !"));
    }

    @Test
    void testMessageProcessorDataApplicationName() {
        Message msg = new Message("Je m'appelle Bob", false, 1);
        Message newMsg = processor.normalize(msg.getMessage());
        processor.easyAnswer(newMsg);

        assertThat(processor.getMessageList().pullLastMessage().getMessage(), is("Bonjour Bob."));
    }

    @Test
    void testMessageProcessorRandomResponse() {
        Message msg = new Message("Greg", false, 1);
        Message newMsg = processor.normalize(msg.getMessage());
        processor.easyAnswer(newMsg);
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

    @Test
    void testMessageProcessorChoiceAnswer() {
        Message msg = new Message("Quel est mon nom ?", false, 1);
        Message newMsg = processor.normalize(msg.getMessage());
        processor.easyAnswer(newMsg);

        assertThat(processor.getMessageList().pullLastMessage().getMessage(),
                is("Je ne connais pas votre nom."));

        msg = new Message("Je m'appelle Bob", false, 3);
        newMsg = processor.normalize(msg.getMessage());
        processor.easyAnswer(newMsg);

        msg = new Message("Quel est mon nom ?", false, 5);
        newMsg = processor.normalize(msg.getMessage());
        processor.easyAnswer(newMsg);

        assertThat(processor.getMessageList().pullLastMessage().getMessage(),
                is("Votre nom est Bob."));
    }
}
