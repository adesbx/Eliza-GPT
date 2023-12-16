package fr.univ_lyon1.info.m1.elizagpt.model.Filter;


import fr.univ_lyon1.info.m1.elizagpt.model.Message.MessageList;

/**
 * Interface for Filter.
 */
public interface Filter {

    /**
     * Apply a filter.
     *
     * @param messageList the messageList to use
     * @param searchText the text we want to search for
     */
    void doFilter(String searchText, MessageList messageList);

    /**
     * Possibility to print the name of the class.
     */
    String toString();
}
