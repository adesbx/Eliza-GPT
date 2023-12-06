package fr.univ_lyon1.info.m1.elizagpt.model;


/**
 * Class for Filter.
 */
public abstract class Filter {

    /**
     * Apply a filter.
     *
     * @param messageList
     * @param searchText
     */
    public abstract MessageList doFilter(String searchText, MessageList messageList);
}
