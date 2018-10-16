package seedu.lostandfound.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.lostandfound.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.lostandfound.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.lostandfound.logic.commands.CommandTestUtil.showArticleAtIndex;
import static seedu.lostandfound.testutil.TypicalArticles.getTypicalArticleList;
import static seedu.lostandfound.testutil.TypicalIndexes.INDEX_FIRST_ARTICLE;
import static seedu.lostandfound.testutil.TypicalIndexes.INDEX_SECOND_ARTICLE;
import static seedu.lostandfound.testutil.TypicalIndexes.INDEX_THIRD_ARTICLE;

import org.junit.Rule;
import org.junit.Test;

import seedu.lostandfound.commons.core.Messages;
import seedu.lostandfound.commons.core.index.Index;
import seedu.lostandfound.commons.events.ui.JumpToListRequestEvent;
import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.ModelManager;
import seedu.lostandfound.model.UserPrefs;
import seedu.lostandfound.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class SelectCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalArticleList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalArticleList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastArticleIndex = Index.fromOneBased(model.getFilteredArticleList().size());

        assertExecutionSuccess(INDEX_FIRST_ARTICLE);
        assertExecutionSuccess(INDEX_THIRD_ARTICLE);
        assertExecutionSuccess(lastArticleIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredArticleList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_ARTICLE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showArticleAtIndex(model, INDEX_FIRST_ARTICLE);
        showArticleAtIndex(expectedModel, INDEX_FIRST_ARTICLE);

        assertExecutionSuccess(INDEX_FIRST_ARTICLE);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showArticleAtIndex(model, INDEX_FIRST_ARTICLE);
        showArticleAtIndex(expectedModel, INDEX_FIRST_ARTICLE);

        Index outOfBoundsIndex = INDEX_SECOND_ARTICLE;
        // ensures that outOfBoundIndex is still in bounds of article list list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getArticleList().getArticleList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_ARTICLE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_ARTICLE);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_ARTICLE);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_ARTICLE);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different article -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCommand selectCommand = new SelectCommand(index);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_ARTICLE_SUCCESS, index.getOneBased());

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCommand selectCommand = new SelectCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}
