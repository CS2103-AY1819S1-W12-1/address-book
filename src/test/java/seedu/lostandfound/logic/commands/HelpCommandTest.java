package seedu.lostandfound.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.lostandfound.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.lostandfound.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import seedu.lostandfound.commons.events.ui.ShowHelpRequestEvent;
import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.ModelManager;
import seedu.lostandfound.ui.testutil.EventsCollectorRule;

public class HelpCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_help_success() {
        assertCommandSuccess(new HelpCommand(), model, commandHistory, SHOWING_HELP_MESSAGE, expectedModel);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowHelpRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
