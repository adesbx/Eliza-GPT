package fr.univ_lyon1.info.m1.elizagpt.model.SelectAnswer;

import fr.univ_lyon1.info.m1.elizagpt.model.DataType;
import fr.univ_lyon1.info.m1.elizagpt.model.DataApplication;

public class ChoiceAnswer<T> implements SelectAnswer {
    private T choiceOne;

    private T choiceTwo;

    private DataType typeOne;

    private DataApplication<T> dataApplication;

    public ChoiceAnswer(T newChoiceOne, DataType newTypeOne,T newChoiceTwo, DataApplication<T> newDataApplication) {
        choiceOne = newChoiceOne;
        choiceTwo = newChoiceTwo;
        typeOne = newTypeOne;
        dataApplication = newDataApplication;
    }

    @Override
    public T execute() {
        if(dataApplication.get(typeOne) != null) {
            return choiceOne;
        }
        return choiceTwo;
    }

}
