package fr.univ_lyon1.info.m1.elizagpt.observer;

/**
 * Observer for the messages.
 */

public interface ProcessorObserver {
    /**
     * Donne l'action a réaliser quand il est notifié.
     */
    void processorUpdated();
}
