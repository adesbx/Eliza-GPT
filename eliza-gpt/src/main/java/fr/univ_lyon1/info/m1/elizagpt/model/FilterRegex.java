package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for filter regular expression.
 */
public class FilterRegex implements Filter {

    /**
     * Apply a filter with a regular expression.
     *
     * @param searchText  The text to search.
     * @param messageList The list of messages to filter.
     */
    @Override
    public void doFilter(final String searchText, final MessageList messageList) {
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(".*" + searchText + ".*", Pattern.CASE_INSENSITIVE);
        ArrayList<Integer> listToRemove = new ArrayList<>();
        if (searchText == null) {
            messageList.removeAll();
        }
        for (Message message : messageList.pullAllMessage()) {
            matcher = pattern.matcher(message.getMessage());
            if (!matcher.matches()) {
                listToRemove.add(message.getId());
            }
        }
        for (Integer id : listToRemove) {
            messageList.remove(id);
        }
    }

    /**
     * Possibility to print the name of the class.
     */
    @Override
    public String toString() {
        return "Regex";
    }
}
