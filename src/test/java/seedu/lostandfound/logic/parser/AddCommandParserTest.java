package seedu.lostandfound.logic.parser;

import static seedu.lostandfound.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.lostandfound.logic.commands.CommandTestUtil.DESCRIPTION_DESC_AMY;
import static seedu.lostandfound.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BOB;
import static seedu.lostandfound.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.lostandfound.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.lostandfound.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.lostandfound.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.lostandfound.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.lostandfound.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.lostandfound.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.lostandfound.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.lostandfound.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.lostandfound.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.lostandfound.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.lostandfound.testutil.TypicalArticles.AMY;
import static seedu.lostandfound.testutil.TypicalArticles.BOB;

import org.junit.Test;

import seedu.lostandfound.logic.commands.AddCommand;
import seedu.lostandfound.model.article.Article;
import seedu.lostandfound.model.article.Description;
import seedu.lostandfound.model.article.Email;
import seedu.lostandfound.model.article.Name;
import seedu.lostandfound.model.article.Phone;
import seedu.lostandfound.model.tag.Tag;
import seedu.lostandfound.testutil.ArticleBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Article expectedArticle = new ArticleBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + DESCRIPTION_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedArticle));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + DESCRIPTION_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedArticle));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + DESCRIPTION_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedArticle));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + DESCRIPTION_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedArticle));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + DESCRIPTION_DESC_AMY
                + DESCRIPTION_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedArticle));

        // multiple tags - all accepted
        Article expectedArticleMultipleTags = new ArticleBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + DESCRIPTION_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedArticleMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Article expectedArticle = new ArticleBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + DESCRIPTION_DESC_AMY,
                new AddCommand(expectedArticle));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + DESCRIPTION_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + DESCRIPTION_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + DESCRIPTION_DESC_BOB,
                expectedMessage);

        // missing description prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_DESCRIPTION_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_DESCRIPTION_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + DESCRIPTION_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + DESCRIPTION_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + DESCRIPTION_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_DESCRIPTION_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Description.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + DESCRIPTION_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_DESCRIPTION_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + DESCRIPTION_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
