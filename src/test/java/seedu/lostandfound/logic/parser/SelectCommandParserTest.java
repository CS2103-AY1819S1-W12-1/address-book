package seedu.lostandfound.logic.parser;

import static seedu.lostandfound.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.lostandfound.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.lostandfound.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.lostandfound.testutil.TypicalIndexes.INDEX_FIRST_ARTICLE;

import org.junit.Test;

import seedu.lostandfound.logic.commands.SelectCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SelectCommandParserTest {

    private SelectCommandParser parser = new SelectCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new SelectCommand(INDEX_FIRST_ARTICLE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
    }
}
