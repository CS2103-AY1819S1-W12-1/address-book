package seedu.lostandfound.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.lostandfound.logic.parser.CliSyntax.PREFIX_OWNER;
import static seedu.lostandfound.model.Model.NOT_RESOLVED_PREDICATE;

import java.util.List;

import seedu.lostandfound.commons.core.Messages;
import seedu.lostandfound.commons.core.index.Index;
import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.logic.commands.exceptions.CommandException;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.article.Article;
import seedu.lostandfound.model.article.Name;

/**
 * Resolves an article identified using it's displayed index from the address book.
 */
public class ResolveCommand extends Command {
    public static final String COMMAND_WORD = "resolve";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Resolves the article identified by the index number used in the displayed article list"
            + " when owner claims their article.\n"
            + "Parameters: INDEX (must be a positive integer) " + PREFIX_OWNER + "OWNER\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_OWNER + "John";

    public static final String MESSAGE_RESOLVED_ARTICLE_SUCCESS = "Resolved Article: %1$s";
    public static final String MESSAGE_NOT_RESOLVED = "Owner need to be specified!";

    private static final boolean SET_ISRESOLVED = true;

    private final Index targetIndex;
    private final Name owner;

    public ResolveCommand(Index targetIndex, Name owner) {
        this.targetIndex = targetIndex;
        this.owner = owner;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Article> lastShownList = model.getFilteredArticleList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ARTICLE_DISPLAYED_INDEX);
        }

        Article articleToEdit = lastShownList.get(targetIndex.getZeroBased());

        if (articleToEdit.getIsResolved()) {
            throw new CommandException(Messages.MESSAGE_RESOLVE_INVALID_RESOLVED);
        }

        Article editedArticle = new Article(articleToEdit.getName(), articleToEdit.getPhone(),
                articleToEdit.getEmail(), articleToEdit.getDescription(), articleToEdit.getImage(),
                articleToEdit.getFinder(), owner, SET_ISRESOLVED, articleToEdit.getTags());

        model.updateArticle(articleToEdit, editedArticle);
        model.updateFilteredArticleList(NOT_RESOLVED_PREDICATE);
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
