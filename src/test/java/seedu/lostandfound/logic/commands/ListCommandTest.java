package seedu.lostandfound.logic.commands;

import static seedu.lostandfound.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.lostandfound.logic.commands.CommandTestUtil.showArticleAtIndex;
import static seedu.lostandfound.testutil.TypicalArticles.getTypicalArticleList;
import static seedu.lostandfound.testutil.TypicalIndexes.INDEX_FIRST_ARTICLE;

import org.junit.Before;
import org.junit.Test;

import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.ModelManager;
import seedu.lostandfound.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalArticleList(), new UserPrefs());
        expectedModel = new ModelManager(model.getArticleList(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showArticleAtIndex(model, INDEX_FIRST_ARTICLE);
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
