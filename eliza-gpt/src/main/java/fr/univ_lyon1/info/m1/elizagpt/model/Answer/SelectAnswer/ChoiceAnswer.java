package fr.univ_lyon1.info.m1.elizagpt.model.Answer.SelectAnswer;

import fr.univ_lyon1.info.m1.elizagpt.model.Answer.DataType;
import fr.univ_lyon1.info.m1.elizagpt.model.Answer.DataApplication;

/**
 * Choice the complex answer if we have the data, else...
 * @param <T>
 */
public class ChoiceAnswer<T> extends SelectAnswer<T> {
    private T choiceOne;

    private T choiceTwo;

    private DataType typeOne;

    private DataApplication<T> dataApplication;

    /**
     * Constructor of ChoiceAnswer.
     * @param newChoiceOne
     * @param newTypeOne
     * @param newChoiceTwo
     * @param newDataApplication
     */
    public ChoiceAnswer(final T newChoiceOne, final DataType newTypeOne,
                        final T newChoiceTwo, final DataApplication<T> newDataApplication) {
        choiceOne = newChoiceOne;
        choiceTwo = newChoiceTwo;
        typeOne = newTypeOne;
        dataApplication = newDataApplication;
    }

    @Override
    public T execute() {
        if (dataApplication.get(typeOne) != null) {
            this.setCurrentAnswer(choiceOne);
        } else {
            this.setCurrentAnswer(choiceTwo);
        }
        return this.getCurrentAnswer();
    }

}
