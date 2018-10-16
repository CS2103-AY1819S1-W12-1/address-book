package seedu.lostandfound.logic.commands;

import static seedu.lostandfound.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.lostandfound.testutil.TypicalArticles.getTypicalArticleList;

import org.junit.Test;

import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.model.ArticleList;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.ModelManager;
import seedu.lostandfound.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyArticleList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitArticleList();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyArticleList_success() {
        Model model = new ModelManager(getTypicalArticleList(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalArticleList(), new UserPrefs());
        expectedModel.resetData(new ArticleList());
        expectedModel.commitArticleList();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
