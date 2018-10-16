package seedu.lostandfound.logic;

import javafx.collections.ObservableList;
import seedu.lostandfound.logic.commands.CommandResult;
import seedu.lostandfound.logic.commands.exceptions.CommandException;
import seedu.lostandfound.logic.parser.exceptions.ParseException;
import seedu.lostandfound.model.article.Article;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /** Returns an unmodifiable view of the filtered list of articles */
    ObservableList<Article> getFilteredArticleList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
