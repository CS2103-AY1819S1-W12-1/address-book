package seedu.lostandfound.logic.parser;

import static seedu.lostandfound.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.lostandfound.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.lostandfound.logic.commands.AddCommand;
import seedu.lostandfound.logic.commands.ClearCommand;
import seedu.lostandfound.logic.commands.Command;
import seedu.lostandfound.logic.commands.DeleteCommand;
import seedu.lostandfound.logic.commands.EditCommand;
import seedu.lostandfound.logic.commands.ExitCommand;
import seedu.lostandfound.logic.commands.FindCommand;
import seedu.lostandfound.logic.commands.HelpCommand;
import seedu.lostandfound.logic.commands.HistoryCommand;
import seedu.lostandfound.logic.commands.ListCommand;
import seedu.lostandfound.logic.commands.RedoCommand;
import seedu.lostandfound.logic.commands.ResolveCommand;
import seedu.lostandfound.logic.commands.SelectCommand;
import seedu.lostandfound.logic.commands.UndoCommand;
import seedu.lostandfound.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ArticleListParser {

    /**
     * Used for initial separation of command word and args.
     */
    //allow only alphabetic letters as commandWord
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>[a-zA-Z]+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ResolveCommand.COMMAND_WORD:
            return new ResolveCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
