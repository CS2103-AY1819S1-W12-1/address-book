package seedu.lostandfound.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.model.ArticleList;
import seedu.lostandfound.model.Model;

/**
 * Clears the article list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Article list has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new ArticleList());
        model.commitArticleList();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
