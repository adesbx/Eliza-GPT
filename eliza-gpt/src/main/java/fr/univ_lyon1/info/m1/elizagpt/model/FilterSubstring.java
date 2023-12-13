package fr.univ_lyon1.info.m1.elizagpt.model;


import java.util.ArrayList;

/**
 * Class for filter substring.
 */
public class FilterSubstring implements Filter {

    /**
     * Apply a filter with a substring.
     * @param messageList
     * @param searchText
     */
    @Override
    public void doFilter(final String searchText, final MessageList messageList) {
        ArrayList<Integer> listToRemove = new ArrayList<>();
        if (searchText == null) {
            messageList.removeAll();
        }
        for (Message message : messageList.pullAllMessage()) {
            if (!message.getMessage().contains(searchText)) {
                listToRemove.add(message.getId());
            }
        }
        for (Integer id: listToRemove) {
            messageList.remove(id);
        }
    }

    /**
     * Possibility to print the name of the class.
     */
    @Override
    public String toString()  {
        return "Substring";
    }
}
