package seedu.lostandfound.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.lostandfound.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.lostandfound.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.lostandfound.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.lostandfound.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.lostandfound.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.lostandfound.commons.core.index.Index;
import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.logic.commands.exceptions.CommandException;
import seedu.lostandfound.model.ArticleList;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.article.Article;
import seedu.lostandfound.model.article.NameContainsKeywordsPredicate;
import seedu.lostandfound.testutil.EditArticleDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_DESCRIPTION_AMY = "Block 312, Amy Street 1";
    public static final String VALID_DESCRIPTION_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String DESCRIPTION_DESC_AMY = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_AMY;
    public static final String DESCRIPTION_DESC_BOB = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_DESCRIPTION_DESC =
            " " + PREFIX_DESCRIPTION; // empty string is allowed for descriptions
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditArticleDescriptor DESC_AMY;
    public static final EditCommand.EditArticleDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditArticleDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withDescription(VALID_DESCRIPTION_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditArticleDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withDescription(VALID_DESCRIPTION_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the article list and the filtered article list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ArticleList expectedArticleList = new ArticleList(actualModel.getArticleList());
        List<Article> expectedFilteredList = new ArrayList<>(actualModel.getFilteredArticleList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedArticleList, actualModel.getArticleList());
            assertEquals(expectedFilteredList, actualModel.getFilteredArticleList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the article at the given {@code targetIndex} in the
     * {@code model}'s article list.
     */
    public static void showArticleAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredArticleList().size());

        Article article = model.getFilteredArticleList().get(targetIndex.getZeroBased());
        final String[] splitName = article.getName().fullName.split("\\s+");
        model.updateFilteredArticleList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredArticleList().size());
    }

    /**
     * Deletes the first article in {@code model}'s filtered list from {@code model}'s article list.
     */
    public static void deleteFirstArticle(Model model) {
        Article firstArticle = model.getFilteredArticleList().get(0);
        model.deleteArticle(firstArticle);
        model.commitArticleList();
    }

}
