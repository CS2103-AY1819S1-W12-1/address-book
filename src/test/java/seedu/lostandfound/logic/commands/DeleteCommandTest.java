package seedu.lostandfound.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.lostandfound.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.lostandfound.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.lostandfound.logic.commands.CommandTestUtil.showArticleAtIndex;
import static seedu.lostandfound.testutil.TypicalArticles.getTypicalArticleList;
import static seedu.lostandfound.testutil.TypicalIndexes.INDEX_FIRST_ARTICLE;
import static seedu.lostandfound.testutil.TypicalIndexes.INDEX_SECOND_ARTICLE;

import org.junit.Test;

import seedu.lostandfound.commons.core.Messages;
import seedu.lostandfound.commons.core.index.Index;
import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.ModelManager;
import seedu.lostandfound.model.UserPrefs;
import seedu.lostandfound.model.article.Article;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalArticleList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Article articleToDelete = model.getFilteredArticleList().get(INDEX_FIRST_ARTICLE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ARTICLE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ARTICLE_SUCCESS, articleToDelete);

        ModelManager expectedModel = new ModelManager(model.getArticleList(), new UserPrefs());
        expectedModel.deleteArticle(articleToDelete);
        expectedModel.commitArticleList();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredArticleList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_ARTICLE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showArticleAtIndex(model, INDEX_FIRST_ARTICLE);

        Article articleToDelete = model.getFilteredArticleList().get(INDEX_FIRST_ARTICLE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ARTICLE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ARTICLE_SUCCESS, articleToDelete);

        Model expectedModel = new ModelManager(model.getArticleList(), new UserPrefs());
        expectedModel.deleteArticle(articleToDelete);
        expectedModel.commitArticleList();
        showNoArticle(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showArticleAtIndex(model, INDEX_FIRST_ARTICLE);

        Index outOfBoundIndex = INDEX_SECOND_ARTICLE;
        // ensures that outOfBoundIndex is still in bounds of article list list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getArticleList().getArticleList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_ARTICLE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Article articleToDelete = model.getFilteredArticleList().get(INDEX_FIRST_ARTICLE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ARTICLE);
        Model expectedModel = new ModelManager(model.getArticleList(), new UserPrefs());
        expectedModel.deleteArticle(articleToDelete);
        expectedModel.commitArticleList();

        // delete -> first article deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts articlelist back to previous state and filtered article list to show all articles
        expectedModel.undoArticleList();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first article deleted again
        expectedModel.redoArticleList();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredArticleList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> article list state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_ARTICLE_DISPLAYED_INDEX);

        // single article list state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Article} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted article in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the article object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameArticleDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ARTICLE);
        Model expectedModel = new ModelManager(model.getArticleList(), new UserPrefs());

        showArticleAtIndex(model, INDEX_SECOND_ARTICLE);
        Article articleToDelete = model.getFilteredArticleList().get(INDEX_FIRST_ARTICLE.getZeroBased());
        expectedModel.deleteArticle(articleToDelete);
        expectedModel.commitArticleList();

        // delete -> deletes second article in unfiltered article list / first article in filtered article list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts articlelist back to previous state and filtered article list to show all articles
        expectedModel.undoArticleList();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(articleToDelete, model.getFilteredArticleList().get(INDEX_FIRST_ARTICLE.getZeroBased()));
        // redo -> deletes same second article in unfiltered article list
        expectedModel.redoArticleList();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_ARTICLE);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_ARTICLE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_ARTICLE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different article -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoArticle(Model model) {
        model.updateFilteredArticleList(p -> false);

        assertTrue(model.getFilteredArticleList().isEmpty());
    }
}
