package seedu.lostandfound.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.lostandfound.commons.core.Messages;
import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.model.Model;

/**
 * Finds and lists all articles in article list whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "-(FIELD): Finds all articles which field contain all of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "FIELD: n(Name), d(Description), f(Finder), t(Tag)\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "-n alice";

    private final Predicate predicate;

    public FindCommand(Predicate predicate) {
        this.predicate = predicate;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredArticleList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_ARTICLES_LISTED_OVERVIEW, model.getFilteredArticleList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
