package seedu.lostandfound.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.lostandfound.model.Model.NOT_RESOLVED_PREDICATE;

import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.model.Model;

/**
 * Lists all articles in the article list to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all unresolved articles";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredArticleList(NOT_RESOLVED_PREDICATE);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
