package seedu.lostandfound.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.lostandfound.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.Rule;
import org.junit.Test;

import seedu.lostandfound.commons.events.ui.ExitAppRequestEvent;
import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.ModelManager;
import seedu.lostandfound.ui.testutil.EventsCollectorRule;

public class ExitCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_exit_success() {
        CommandResult result = new ExitCommand().execute(model, commandHistory);
        assertEquals(MESSAGE_EXIT_ACKNOWLEDGEMENT, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ExitAppRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
