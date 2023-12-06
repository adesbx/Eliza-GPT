package fr.univ_lyon1.info.m1.elizagpt.model;



/**
 * Class for filter substring.
 */
public class FilterSubstring extends Filter {

    /**
     * Apply a filter with a substring.
     * @param messageList
     * @param searchText
     */
    @Override
    public MessageList doFilter(final String searchText, final MessageList messageList) {
        for (Message message : messageList.pullAllMessage()) {
            if (!message.getMessage().equals(searchText)) {
                messageList.remove(message.getId());
            }
        }
        return messageList;
    }
}
