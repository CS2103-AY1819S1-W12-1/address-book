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

public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalArticleList(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalArticleList(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        deleteFirstArticle(model);
        deleteFirstArticle(model);

        deleteFirstArticle(expectedModel);
        deleteFirstArticle(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoArticleList();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoArticleList();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }
}
