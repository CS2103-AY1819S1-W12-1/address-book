package seedu.lostandfound.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.lostandfound.commons.core.EventsCenter;
import seedu.lostandfound.commons.core.Messages;
import seedu.lostandfound.commons.core.index.Index;
import seedu.lostandfound.commons.events.ui.JumpToListRequestEvent;
import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.logic.commands.exceptions.CommandException;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.article.Article;

/**
 * Selects a article identified using it's displayed index from the article list.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the article identified by the index number used in the displayed article list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_ARTICLE_SUCCESS = "Selected Article: %1$s";

    private final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Article> filteredArticleList = model.getFilteredArticleList();

        if (targetIndex.getZeroBased() >= filteredArticleList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ARTICLE_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_ARTICLE_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
