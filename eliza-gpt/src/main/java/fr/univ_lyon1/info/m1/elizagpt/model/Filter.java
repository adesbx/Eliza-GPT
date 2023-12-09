package fr.univ_lyon1.info.m1.elizagpt.model;


/**
 * Interface for Filter.
 */
public interface Filter {

    /**
     * Apply a filter.
     *
     * @param messageList
     * @param searchText
     */
    void doFilter(String searchText, MessageList messageList);

    /**
     * Possibility to print the name of the class.
     */
    String toString();
}
