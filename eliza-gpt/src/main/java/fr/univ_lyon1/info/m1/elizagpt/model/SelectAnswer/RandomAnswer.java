package fr.univ_lyon1.info.m1.elizagpt.model.SelectAnswer;

import java.util.Random;

public class RandomAnswer<T> implements SelectAnswer<T> {

    private T[] object;
    private final Random random = new Random();
    public RandomAnswer(T[] newObject) {
        object = newObject;
    }

    @Override
    public T execute() {
        return pickRandom(this.object);
    }

    /**
     * Pick an element randomly in the array.
     */
    public <T> T pickRandom(final T[] array) {
        return array[random.nextInt(array.length)];
    }
}
