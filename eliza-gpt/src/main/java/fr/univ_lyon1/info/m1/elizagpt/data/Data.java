package fr.univ_lyon1.info.m1.elizagpt.data;

public class Data {
    private String message = null;
    private Boolean isAnswer = null;

    public Data(String message_, Boolean isAnswer_) {
        message = message_;
        isAnswer = isAnswer_;
    }

    public String getMessage() {
        return message;
    }

    public boolean getIsAnswer() {
        return isAnswer;
    }
}
