package seedu.lostandfound.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.lostandfound.commons.core.Messages.MESSAGE_ARTICLES_LISTED_OVERVIEW;
import static seedu.lostandfound.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.lostandfound.testutil.TypicalArticles.CARL;
import static seedu.lostandfound.testutil.TypicalArticles.getTypicalArticleList;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.ModelManager;
import seedu.lostandfound.model.UserPrefs;
import seedu.lostandfound.model.article.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalArticleList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalArticleList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different article -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noArticleFound() {
        String expectedMessage = String.format(MESSAGE_ARTICLES_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate("-n  ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredArticleList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredArticleList());
    }

    @Test
    public void execute_multipleKeywords_oneArticleFound() {
        String expectedMessage = String.format(MESSAGE_ARTICLES_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate predicate = preparePredicate("Carl Kurz Kurz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredArticleList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL), model.getFilteredArticleList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
