package fr.univ_lyon1.info.m1.elizagpt.view;

/**
 * Implement observer interface from java.
 */
public interface Observer {
    /**
     * This method is called whenever the observed object is changed. An
     * application calls an {@code Observable} object's
     * {@code notifyObservers} method to have all the object's
     * observers notified of the change.
     */
    void update();
}
