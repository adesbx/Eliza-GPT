package fr.univ_lyon1.info.m1.elizagpt.model.SelectAnswer;

/**
 * The Answer is type T.
 * @param <T>
 */
public class SimpleAnswer<T> extends SelectAnswer<T> {


    /**
     * builder of simpleAnswer.
     * @param newAnswer
     */
    public SimpleAnswer(final T newAnswer) {
        this.setCurrentAnswer(newAnswer);
    }

    @Override
    public T execute() {
        return this.getCurrentAnswer();
    }
}
