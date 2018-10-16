package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.lostandfound.commons.core.Messages.MESSAGE_ARTICLES_LISTED_OVERVIEW;
import static seedu.lostandfound.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.lostandfound.testutil.TypicalArticles.BENSON;
import static seedu.lostandfound.testutil.TypicalArticles.CARL;
import static seedu.lostandfound.testutil.TypicalArticles.DANIEL;
import static seedu.lostandfound.testutil.TypicalArticles.KEYWORD_MATCHING_MEIER;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.lostandfound.commons.core.index.Index;
import seedu.lostandfound.logic.commands.DeleteCommand;
import seedu.lostandfound.logic.commands.FindCommand;
import seedu.lostandfound.logic.commands.RedoCommand;
import seedu.lostandfound.logic.commands.UndoCommand;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.tag.Tag;

public class FindCommandSystemTest extends ArticleListSystemTest {

    @Test
    public void find() {
        /* Case: find multiple articles in article list, command with leading spaces and trailing spaces
         * -> 2 articles found
         */
        String command = "   " + FindCommand.COMMAND_WORD + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where article list is displaying the articles we are finding
         * -> 2 articles found
         */
        command = FindCommand.COMMAND_WORD + KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same articles in article list after deleting 1 of them -> 1 article found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getArticleList().getArticleList().contains(BENSON));
        command = FindCommand.COMMAND_WORD + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find article where article list is not displaying the article we are finding -> 1 article found */
        command = FindCommand.COMMAND_WORD + "-n Carl";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find article in article list, 2 keywords -> 1 article found */
        command = FindCommand.COMMAND_WORD + "-n Daniel Meier";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find article in article list, 2 keywords in reversed order -> 1 article found */
        command = FindCommand.COMMAND_WORD + "-n Meier Daniel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find article in article list, 2 keywords with 1 repeat -> 1 article found */
        command = FindCommand.COMMAND_WORD + "-n Daniel Daniel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find article in article list, keyword is same as name but of different case -> 1 article found */
        command = FindCommand.COMMAND_WORD + "-n MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple articles in article list, 2 matching keywords and 1 non-matching keyword
         * -> 0 article found
         */
        command = FindCommand.COMMAND_WORD + "-n Daniel Daniel NonMatchingKeyWord";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find article in article list, keyword is substring of name -> 0 articles found */
        command = FindCommand.COMMAND_WORD + "-n Mei";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find article in article list, name is substring of keyword -> 0 articles found */
        command = FindCommand.COMMAND_WORD + "-n Meiers";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find article not in article list -> 0 articles found */
        command = FindCommand.COMMAND_WORD + "-n Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of article in article list -> 0 articles found */
        command = FindCommand.COMMAND_WORD + "-n " + DANIEL.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find description of article in article list -> 0 articles found */
        command = FindCommand.COMMAND_WORD + "-n " + DANIEL.getDescription().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of article in article list -> 0 articles found */
        command = FindCommand.COMMAND_WORD + "-n " + DANIEL.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of article in article list -> 0 articles found */
        List<Tag> tags = new ArrayList<>(DANIEL.getTags());
        command = FindCommand.COMMAND_WORD + "-n " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a article is selected -> selected card deselected */
        showAllArticles();
        selectArticle(Index.fromOneBased(1));
        assertFalse(getArticleListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName));
        command = FindCommand.COMMAND_WORD + "-n Daniel";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find article in empty article list -> 0 articles found */
        deleteAllArticles();
        command = FindCommand.COMMAND_WORD + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_ARTICLES_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code ArticleListSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see ArticleListSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_ARTICLES_LISTED_OVERVIEW, expectedModel.getFilteredArticleList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code ArticleListSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
