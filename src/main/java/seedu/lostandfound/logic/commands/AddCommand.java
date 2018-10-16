package seedu.lostandfound.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.lostandfound.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.lostandfound.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.lostandfound.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.lostandfound.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.lostandfound.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.logic.commands.exceptions.CommandException;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.article.Article;

/**
 * Adds a article to the article list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a article to the article list. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_DESCRIPTION + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New article added: %1$s";
    public static final String MESSAGE_DUPLICATE_ARTICLE = "This article already exists in the article list";

    private final Article toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Article}
     */
    public AddCommand(Article article) {
        requireNonNull(article);
        toAdd = article;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasArticle(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ARTICLE);
        }

        model.addArticle(toAdd);
        model.commitArticleList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
