package seedu.lostandfound.logic.parser;

import static seedu.lostandfound.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import seedu.lostandfound.MainApp;
import seedu.lostandfound.commons.core.LogsCenter;
import seedu.lostandfound.logic.commands.FindCommand;
import seedu.lostandfound.logic.parser.exceptions.ParseException;
import seedu.lostandfound.model.article.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        logger.info((Arrays.toString(nameKeywords)));
        List<String> listKeywords = new LinkedList<>(Arrays.asList(nameKeywords));

        if (listKeywords.get(0).equals("-n")) { //different operations depending on FIELD
            listKeywords.remove(0);
            return new FindCommand(new NameContainsKeywordsPredicate(listKeywords));
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }
}
