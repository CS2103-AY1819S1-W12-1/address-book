package seedu.lostandfound.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.lostandfound.commons.core.Messages;
import seedu.lostandfound.commons.core.index.Index;
import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.logic.commands.exceptions.CommandException;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.article.Article;

/**
 * Deletes a article identified using it's displayed index from the article list.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the article identified by the index number used in the displayed article list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ARTICLE_SUCCESS = "Deleted Article: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Article> lastShownList = model.getFilteredArticleList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ARTICLE_DISPLAYED_INDEX);
        }

        Article articleToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteArticle(articleToDelete);
        model.commitArticleList();
        return new CommandResult(String.format(MESSAGE_DELETE_ARTICLE_SUCCESS, articleToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
