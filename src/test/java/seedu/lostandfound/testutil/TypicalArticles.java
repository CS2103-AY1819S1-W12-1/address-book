package seedu.lostandfound.testutil;

import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMY;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.lostandfound.model.ArticleList;
import seedu.lostandfound.model.article.Article;

/**
 * A utility class containing a list of {@code Article} objects to be used in tests.
 */
public class TypicalArticles {

    public static final Article ALICE = new ArticleBuilder().withName("Alice Pauline")
            .withDescription("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Article BENSON = new ArticleBuilder().withName("Benson Meier")
            .withDescription("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Article CARL = new ArticleBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withDescription("wall street").build();
    public static final Article DANIEL = new ArticleBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withDescription("10th street").withTags("friends").build();
    public static final Article ELLE = new ArticleBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withDescription("michegan ave").build();
    public static final Article FIONA = new ArticleBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withDescription("little tokyo").build();
    public static final Article GEORGE = new ArticleBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withDescription("4th street").build();

    // Manually added
    public static final Article HOON = new ArticleBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withDescription("little india").build();
    public static final Article IDA = new ArticleBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withDescription("chicago ave").build();

    // Manually added - Article's details found in {@code CommandTestUtil}
    public static final Article AMY = new ArticleBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withDescription(VALID_DESCRIPTION_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Article BOB = new ArticleBuilder()
            .withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
            .withDescription(VALID_DESCRIPTION_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "-n Meier"; // A keyword that matches MEIER

    private TypicalArticles() {} // prevents instantiation

    /**
     * Returns an {@code ArticleList} with all the typical articles.
     */
    public static ArticleList getTypicalArticleList() {
        ArticleList ab = new ArticleList();
        for (Article article : getTypicalArticles()) {
            ab.addArticle(article);
        }
        return ab;
    }

    public static List<Article> getTypicalArticles() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
