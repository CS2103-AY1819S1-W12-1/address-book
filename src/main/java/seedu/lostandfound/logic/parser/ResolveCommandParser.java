package seedu.lostandfound.logic.parser;

import static seedu.lostandfound.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.lostandfound.commons.core.index.Index;
import seedu.lostandfound.logic.commands.ResolveCommand;
import seedu.lostandfound.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class ResolveCommandParser implements Parser<ResolveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ResolveCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ResolveCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResolveCommand.MESSAGE_USAGE), pe);
        }
    }

}
