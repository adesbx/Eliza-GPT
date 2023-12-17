package fr.univ_lyon1.info.m1.elizagpt.model.Answer.SelectAnswer;

/**
 * Abstract Class for implement différent way to have the answer.
 * @param <T>
 */
public abstract class SelectAnswer<T> {
    private T currentAnswer = null;

    /**
     * get the currentAnswer.
     * @return T the answer
     */
    protected T getCurrentAnswer() {
        return currentAnswer;
    }

    /**
     * set the new currentAnswer.
     * @param newCurrentAnswer
     */
    protected void setCurrentAnswer(final T newCurrentAnswer) {
        currentAnswer = newCurrentAnswer;
    }

    /**
     * execute return the currentAnswer.
     * @return T
     */
    public abstract T execute();
}
