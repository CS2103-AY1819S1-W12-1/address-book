package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.lostandfound.commons.core.Messages.MESSAGE_INVALID_ARTICLE_DISPLAYED_INDEX;
import static seedu.lostandfound.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.lostandfound.logic.commands.DeleteCommand.MESSAGE_DELETE_ARTICLE_SUCCESS;
import static seedu.lostandfound.testutil.TestUtil.getArticle;
import static seedu.lostandfound.testutil.TestUtil.getLastIndex;
import static seedu.lostandfound.testutil.TestUtil.getMidIndex;
import static seedu.lostandfound.testutil.TypicalArticles.KEYWORD_MATCHING_MEIER;
import static seedu.lostandfound.testutil.TypicalIndexes.INDEX_FIRST_ARTICLE;

import org.junit.Test;

import seedu.lostandfound.commons.core.Messages;
import seedu.lostandfound.commons.core.index.Index;
import seedu.lostandfound.logic.commands.DeleteCommand;
import seedu.lostandfound.logic.commands.RedoCommand;
import seedu.lostandfound.logic.commands.UndoCommand;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.article.Article;

public class DeleteCommandSystemTest extends ArticleListSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first article in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteCommand.COMMAND_WORD
                + "      " + INDEX_FIRST_ARTICLE.getOneBased()
                + "       ";
        Article deletedArticle = removeArticle(expectedModel, INDEX_FIRST_ARTICLE);
        String expectedResultMessage = String.format(MESSAGE_DELETE_ARTICLE_SUCCESS, deletedArticle);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last article in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastArticleIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastArticleIndex);

        /* Case: undo deleting the last article in the list -> last article restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last article in the list -> last article deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeArticle(modelBeforeDeletingLast, lastArticleIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle article in the list -> deleted */
        Index middleArticleIndex = getMidIndex(getModel());
        assertCommandSuccess(middleArticleIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered article list, delete index within bounds of article list and article list -> deleted */
        showArticlesWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_ARTICLE;
        assertTrue(index.getZeroBased() < getModel().getFilteredArticleList().size());
        assertCommandSuccess(index);

        /* Case: filtered article list, delete index within bounds of article list but out of bounds of article list
         * -> rejected
         */
        showArticlesWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getArticleList().getArticleList().size();
        command = DeleteCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_ARTICLE_DISPLAYED_INDEX);

        /* --------------------- Performing delete operation while a article card is selected ----------------------- */

        /* Case: delete the selected article -> article list panel selects the article before the deleted article */
        showAllArticles();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectArticle(selectedIndex);
        command = DeleteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        deletedArticle = removeArticle(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_ARTICLE_SUCCESS, deletedArticle);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getArticleList().getArticleList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_ARTICLE_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Article} at the specified {@code index} in {@code model}'s article list.
     * @return the removed article
     */
    private Article removeArticle(Model model, Index index) {
        Article targetArticle = getArticle(model, index);
        model.deleteArticle(targetArticle);
        return targetArticle;
    }

    /**
     * Deletes the article at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Article deletedArticle = removeArticle(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_ARTICLE_SUCCESS, deletedArticle);

        assertCommandSuccess(
                DeleteCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the article details and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code ArticleListSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see ArticleListSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the article
     * details and selected card are expected to update accordingly depending on the card
     * at {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see ArticleListSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the article details, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
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
