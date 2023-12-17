package fr.univ_lyon1.info.m1.elizagpt.model.Answer.SelectAnswer;

import java.util.Random;

/**
 * The random is T[] and we return a random form table.
 * @param <T>
 */
public class RandomAnswer<T> extends SelectAnswer<T> {

    private T[] object;
    private final Random random = new Random();

    /**
     * Constructor of RandomAnswer.
     * @param newObject
     */
    public RandomAnswer(final T[] newObject) {
        object = newObject;
    }

    @Override
    public T execute() {
        this.setCurrentAnswer(pickRandom(this.object));
        return this.getCurrentAnswer();
    }

    /**
     * Pick an element randomly in the array.
     * @return T which is the random element
     */
    public <T> T pickRandom(final T[] array) {
        return array[random.nextInt(array.length)];
    }
}
