package fr.univ_lyon1.info.m1.elizagpt.model.SelectAnswer;

public class SimpleAnswer<T> implements SelectAnswer {

    T answer;
    public SimpleAnswer(T newAnswer) {
        answer = newAnswer;
    }

    @Override
    public Object execute() {
        return answer;
    }
}
