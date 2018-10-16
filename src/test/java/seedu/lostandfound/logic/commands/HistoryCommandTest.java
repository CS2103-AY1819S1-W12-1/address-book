package seedu.lostandfound.logic.commands;

import static seedu.lostandfound.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.ModelManager;

public class HistoryCommandTest {
    private CommandHistory history = new CommandHistory();
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute() {
        assertCommandSuccess(new HistoryCommand(), model, history, HistoryCommand.MESSAGE_NO_HISTORY, expectedModel);

        String command1 = "clear";
        history.add(command1);
        assertCommandSuccess(new HistoryCommand(), model, history,
                String.format(HistoryCommand.MESSAGE_SUCCESS, command1), expectedModel);

        String command2 = "randomCommand";
        String command3 = "select 1";
        history.add(command2);
        history.add(command3);

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SUCCESS,
                String.join("\n", command3, command2, command1));
        assertCommandSuccess(new HistoryCommand(), model, history, expectedMessage, expectedModel);
    }

}
