package seedu.lostandfound.logic.commands;

import static seedu.lostandfound.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.lostandfound.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.lostandfound.logic.commands.CommandTestUtil.deleteFirstArticle;
import static seedu.lostandfound.testutil.TypicalArticles.getTypicalArticleList;

import org.junit.Before;
import org.junit.Test;

import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.ModelManager;
import seedu.lostandfound.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalArticleList(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalArticleList(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstArticle(model);
        deleteFirstArticle(model);
        model.undoArticleList();
        model.undoArticleList();

        deleteFirstArticle(expectedModel);
        deleteFirstArticle(expectedModel);
        expectedModel.undoArticleList();
        expectedModel.undoArticleList();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoArticleList();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoArticleList();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
