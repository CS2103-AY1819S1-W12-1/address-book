package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.lostandfound.commons.core.Messages.MESSAGE_INVALID_ARTICLE_DISPLAYED_INDEX;
import static seedu.lostandfound.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.lostandfound.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.lostandfound.logic.commands.SelectCommand.MESSAGE_SELECT_ARTICLE_SUCCESS;
import static seedu.lostandfound.testutil.TestUtil.getLastIndex;
import static seedu.lostandfound.testutil.TestUtil.getMidIndex;
import static seedu.lostandfound.testutil.TypicalArticles.KEYWORD_MATCHING_MEIER;
import static seedu.lostandfound.testutil.TypicalIndexes.INDEX_FIRST_ARTICLE;

import org.junit.Test;

import seedu.lostandfound.commons.core.index.Index;
import seedu.lostandfound.logic.commands.RedoCommand;
import seedu.lostandfound.logic.commands.SelectCommand;
import seedu.lostandfound.logic.commands.UndoCommand;
import seedu.lostandfound.model.Model;

public class SelectCommandSystemTest extends ArticleListSystemTest {
    @Test
    public void select() {
        /* ------------------------ Perform select operations on the shown unfiltered list -------------------------- */

        /* Case: select the first card in the article list, command with leading spaces and trailing spaces
         * -> selected
         */
        String command = "   " + SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_ARTICLE.getOneBased() + "   ";
        assertCommandSuccess(command, INDEX_FIRST_ARTICLE);

        /* Case: select the last card in the article list -> selected */
        Index articleCount = getLastIndex(getModel());
        command = SelectCommand.COMMAND_WORD + " " + articleCount.getOneBased();
        assertCommandSuccess(command, articleCount);

        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo selecting last card in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: select the middle card in the article list -> selected */
        Index middleIndex = getMidIndex(getModel());
        command = SelectCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
        assertCommandSuccess(command, middleIndex);

        /* Case: select the current selected card -> selected */
        assertCommandSuccess(command, middleIndex);

        /* ------------------------ Perform select operations on the shown filtered list ---------------------------- */

        /* Case: filtered article list, select index within bounds of article list but out of bounds of article list
         * -> rejected
         */
        showArticlesWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getArticleList().getArticleList().size();
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_ARTICLE_DISPLAYED_INDEX);

        /* Case: filtered article list, select index within bounds of article list and article list -> selected */
        Index validIndex = Index.fromOneBased(1);
        assertTrue(validIndex.getZeroBased() < getModel().getFilteredArticleList().size());
        command = SelectCommand.COMMAND_WORD + " " + validIndex.getOneBased();
        assertCommandSuccess(command, validIndex);

        /* ----------------------------------- Perform invalid select operations ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredArticleList().size() + 1;
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_ARTICLE_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("SeLeCt 1", MESSAGE_UNKNOWN_COMMAND);

        /* Case: select from empty article list -> rejected */
        deleteAllArticles();
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_ARTICLE.getOneBased(),
                MESSAGE_INVALID_ARTICLE_DISPLAYED_INDEX);
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing select command with the
     * {@code expectedSelectedCardIndex} of the selected article.<br>
     * 4. {@code Storage} and {@code ArticleListPanel} remain unchanged.<br>
     * 5. Selected card is at {@code expectedSelectedCardIndex} and the article details is updated accordingly.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ArticleListSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ArticleListSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see ArticleListSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(
                MESSAGE_SELECT_ARTICLE_SUCCESS, expectedSelectedCardIndex.getOneBased());
        int preExecutionSelectedCardIndex = getArticleListPanel().getSelectedCardIndex();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
            assertSelectedCardUnchanged();
        } else {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
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
