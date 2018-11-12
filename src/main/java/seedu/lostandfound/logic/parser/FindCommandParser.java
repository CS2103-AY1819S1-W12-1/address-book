package seedu.lostandfound.logic.parser;

import static seedu.lostandfound.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import seedu.lostandfound.logic.commands.FindCommand;
import seedu.lostandfound.logic.parser.exceptions.ParseException;
import seedu.lostandfound.model.article.DescriptionContainsKeywordsPredicate;
import seedu.lostandfound.model.article.FinderContainsKeywordsPredicate;
import seedu.lostandfound.model.article.NameContainsKeywordsPredicate;
import seedu.lostandfound.model.article.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    private static final String FIND_BY_NAME = "-n";
    private static final String FIND_BY_DESCRIPTION = "-d";
    private static final String FIND_BY_FINDER = "-f";
    private static final String FIND_BY_TAG = "-t";

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

        List<String> listKeywords = new LinkedList<>(Arrays.asList(nameKeywords));

        String parameter = listKeywords.remove(0);

        //different operations depending on FIELD
        switch (parameter) {

        case FIND_BY_NAME:
            return new FindCommand(new NameContainsKeywordsPredicate(listKeywords));

        case FIND_BY_DESCRIPTION:
            return new FindCommand(new DescriptionContainsKeywordsPredicate(listKeywords));

        case FIND_BY_FINDER:
            return new FindCommand(new FinderContainsKeywordsPredicate(listKeywords));

        case FIND_BY_TAG:
            return new FindCommand(new TagContainsKeywordsPredicate(listKeywords));

        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

    }
}
