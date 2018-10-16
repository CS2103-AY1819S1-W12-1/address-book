package seedu.lostandfound.logic.parser;

import static seedu.lostandfound.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.lostandfound.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.lostandfound.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.lostandfound.logic.commands.FindCommand;
import seedu.lostandfound.model.article.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice")));
        assertParseSuccess(parser, "-n Alice", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "-n \n Alice \n \t  \t", expectedFindCommand);
    }

}
