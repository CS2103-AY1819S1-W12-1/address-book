package systemtests;

import static seedu.lostandfound.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.lostandfound.logic.commands.CommandTestUtil.DESCRIPTION_DESC_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.DESCRIPTION_DESC_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.EMAIL_DESC_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.EMAIL_DESC_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.FINDER_DESC_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.FINDER_DESC_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_FINDER_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.NAME_DESC_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.NAME_DESC_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.PHONE_DESC_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.PHONE_DESC_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.TAG_DESC_BLUE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.TAG_DESC_RED;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_EMAIL_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_FINDER_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_NAME_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_PHONE_MOUSE;
import static seedu.lostandfound.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.lostandfound.testutil.TypicalArticles.BAG;
import static seedu.lostandfound.testutil.TypicalArticles.KEYWORD_MATCHING_MEIER;
import static seedu.lostandfound.testutil.TypicalArticles.MOUSE;
import static seedu.lostandfound.testutil.TypicalArticles.NECKLACE;
import static seedu.lostandfound.testutil.TypicalArticles.POWERBANK;
import static seedu.lostandfound.testutil.TypicalArticles.SHIRT;
import static seedu.lostandfound.testutil.TypicalArticles.WATCH;

import org.junit.Test;

import seedu.lostandfound.commons.core.Messages;
import seedu.lostandfound.commons.core.index.Index;
import seedu.lostandfound.logic.commands.AddCommand;
import seedu.lostandfound.logic.commands.RedoCommand;
import seedu.lostandfound.logic.commands.UndoCommand;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.article.Article;
import seedu.lostandfound.model.article.Description;
import seedu.lostandfound.model.article.Email;
import seedu.lostandfound.model.article.Name;
import seedu.lostandfound.model.article.Phone;
import seedu.lostandfound.model.tag.Tag;
import seedu.lostandfound.testutil.ArticleBuilder;
import seedu.lostandfound.testutil.ArticleUtil;

public class AddCommandSystemTest extends ArticleListSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a article without tags to a non-empty article list, command with leading spaces and trailing spaces
         * -> added
         */
        Article toAdd = POWERBANK;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_POWERBANK + "  " + FINDER_DESC_POWERBANK
                + PHONE_DESC_POWERBANK + " " + EMAIL_DESC_POWERBANK + "   " + DESCRIPTION_DESC_POWERBANK + "   "
                + TAG_DESC_RED + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addArticle(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a article with all fields same as another article in the article list except name -> added */
        toAdd = new ArticleBuilder(POWERBANK).withName(VALID_NAME_MOUSE).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_MOUSE + PHONE_DESC_POWERBANK + EMAIL_DESC_POWERBANK
                + FINDER_DESC_POWERBANK + DESCRIPTION_DESC_POWERBANK + TAG_DESC_RED;
        assertCommandSuccess(command, toAdd);

        /* Case: add a article with all fields same as another article in the article list except description
         * -> added
         */
        toAdd = new ArticleBuilder(POWERBANK).withDescription(VALID_DESCRIPTION_MOUSE).build();
        command = ArticleUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty article list -> added */
        deleteAllArticles();
        assertCommandSuccess(BAG);

        /* Case: add a article with tags, command with parameters in random order -> added */
        toAdd = MOUSE;
        command = AddCommand.COMMAND_WORD + TAG_DESC_BLUE + PHONE_DESC_MOUSE + DESCRIPTION_DESC_MOUSE + NAME_DESC_MOUSE
                + FINDER_DESC_MOUSE + TAG_DESC_RED + EMAIL_DESC_MOUSE;
        assertCommandSuccess(command, toAdd);

        /* Case: add a article, missing tags -> added */
        assertCommandSuccess(SHIRT);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the article list before adding -> added */
        showArticlesWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(NECKLACE);

        /* ------------------------ Perform add operation while a article card is selected -------------------------- */

        /* Case: selects first card in the article list, add a article -> added, card selection remains unchanged */
        selectArticle(Index.fromOneBased(1));
        assertCommandSuccess(WATCH);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate article -> rejected */
        command = ArticleUtil.getAddCommand(SHIRT);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ARTICLE);

        /* Case: add a duplicate article except with different phone -> rejected */
        toAdd = new ArticleBuilder(SHIRT).withPhone(VALID_PHONE_MOUSE).build();
        command = ArticleUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ARTICLE);

        /* Case: add a duplicate article except with different email -> rejected */
        toAdd = new ArticleBuilder(SHIRT).withEmail(VALID_EMAIL_MOUSE).build();
        command = ArticleUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ARTICLE);

        /* Case: add a duplicate article except with different finder -> rejected */
        toAdd = new ArticleBuilder(SHIRT).withFinder(VALID_FINDER_MOUSE).build();
        command = ArticleUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ARTICLE);

        /* Case: add a duplicate article except with different tags -> rejected */
        command = ArticleUtil.getAddCommand(SHIRT) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ARTICLE);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + PHONE_DESC_POWERBANK + EMAIL_DESC_POWERBANK
                + FINDER_DESC_POWERBANK + DESCRIPTION_DESC_POWERBANK;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_POWERBANK + EMAIL_DESC_POWERBANK
                + FINDER_DESC_POWERBANK + DESCRIPTION_DESC_POWERBANK;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_POWERBANK + PHONE_DESC_POWERBANK
                + FINDER_DESC_POWERBANK + DESCRIPTION_DESC_POWERBANK;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing description -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_POWERBANK + PHONE_DESC_POWERBANK
                + FINDER_DESC_POWERBANK + EMAIL_DESC_POWERBANK;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing finder -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_POWERBANK + PHONE_DESC_POWERBANK
                + DESCRIPTION_DESC_POWERBANK + EMAIL_DESC_POWERBANK;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + ArticleUtil.getArticleDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_POWERBANK + EMAIL_DESC_POWERBANK
                + FINDER_DESC_POWERBANK + DESCRIPTION_DESC_POWERBANK;
        assertCommandFailure(command, Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_POWERBANK + INVALID_PHONE_DESC + EMAIL_DESC_POWERBANK
                + FINDER_DESC_POWERBANK + DESCRIPTION_DESC_POWERBANK;
        assertCommandFailure(command, Phone.MESSAGE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_POWERBANK + PHONE_DESC_POWERBANK + INVALID_EMAIL_DESC
                + FINDER_DESC_POWERBANK + DESCRIPTION_DESC_POWERBANK;
        assertCommandFailure(command, Email.MESSAGE_CONSTRAINTS);

        /* Case: invalid description -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_POWERBANK + PHONE_DESC_POWERBANK + EMAIL_DESC_POWERBANK
                + FINDER_DESC_POWERBANK + INVALID_DESCRIPTION_DESC;
        assertCommandFailure(command, Description.MESSAGE_CONSTRAINTS);

        /* Case: invalid finder -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_POWERBANK + PHONE_DESC_POWERBANK + EMAIL_DESC_POWERBANK
                + INVALID_FINDER_DESC + DESCRIPTION_DESC_POWERBANK;
        assertCommandFailure(command, Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_POWERBANK + PHONE_DESC_POWERBANK + EMAIL_DESC_POWERBANK
                + FINDER_DESC_POWERBANK + DESCRIPTION_DESC_POWERBANK + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code ArticleListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Article details and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ArticleListSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ArticleListSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Article toAdd) {
        assertCommandSuccess(ArticleUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Article)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Article)
     */
    private void assertCommandSuccess(String command, Article toAdd) {
        Model expectedModel = getModel();
        expectedModel.addArticle(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Article)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code ArticleListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Article)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code ArticleListPanel} remain unchanged.<br>
     * 5. Article details, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ArticleListSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ArticleListSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
