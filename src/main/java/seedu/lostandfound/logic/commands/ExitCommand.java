package seedu.lostandfound.logic.commands;

import seedu.lostandfound.commons.core.EventsCenter;
import seedu.lostandfound.commons.events.ui.ExitAppRequestEvent;
import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Article List as requested ...";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
