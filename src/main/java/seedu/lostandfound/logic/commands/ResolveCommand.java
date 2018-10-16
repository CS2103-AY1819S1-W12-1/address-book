package seedu.lostandfound.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.lostandfound.model.Model.PREDICATE_SHOW_ALL_ARTICLES;

import java.util.List;

import seedu.lostandfound.commons.core.Messages;
import seedu.lostandfound.commons.core.index.Index;
import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.logic.commands.exceptions.CommandException;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.article.Article;

/**
 * Resolves an article identified using it's displayed index from the address book.
 */
public class ResolveCommand extends Command {
    public static final String COMMAND_WORD = "resolve";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Resolves the person identified by the index number used in the displayed article list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private static final String MESSAGE_RESOLVED_ARTICLE_SUCCESS = "Resolved Article: %1$s";
    private static final boolean SET_ISRESOLVED = true;

    private final Index targetIndex;

    public ResolveCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Article> lastShownList = model.getFilteredArticleList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ARTICLE_DISPLAYED_INDEX);
        }

        Article articleToEdit = lastShownList.get(targetIndex.getZeroBased());

        Article editedArticle = new Article(articleToEdit.getName(), articleToEdit.getPhone(),
                articleToEdit.getEmail(), articleToEdit.getDescription(), SET_ISRESOLVED, articleToEdit.getTags());

        model.updateArticle(articleToEdit, editedArticle);
        model.updateFilteredArticleList(PREDICATE_SHOW_ALL_ARTICLES);
        model.commitArticleList();

        return new CommandResult(String.format(MESSAGE_RESOLVED_ARTICLE_SUCCESS, editedArticle));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ResolveCommand // instanceof handles nulls
                && targetIndex.equals(((ResolveCommand) other).targetIndex)); // state check
    }
}
