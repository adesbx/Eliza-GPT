package fr.univ_lyon1.info.m1.elizagpt.model.Filter;

import fr.univ_lyon1.info.m1.elizagpt.model.Message.Message;
import fr.univ_lyon1.info.m1.elizagpt.model.Message.MessageList;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for filter complete word.
 */
public class FilterCompleteWord implements Filter {

    /**
     * Apply a filter, only return if the message contain the complete word.
     *
     * @param searchText  The text to search.
     * @param messageList The list of messages to filter.
     */
    @Override
    public void doFilter(final String searchText, final MessageList messageList) {
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(".*\\b" + searchText + "\\b.*", Pattern.CASE_INSENSITIVE);

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
        return "CompleteWord";
    }
}
