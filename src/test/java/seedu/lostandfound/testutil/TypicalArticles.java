package seedu.lostandfound.testutil;

import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_DESCRIPTION_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_EMAIL_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_EMAIL_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_FINDER_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_FINDER_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_NAME_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_NAME_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_PHONE_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_PHONE_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_TAG_BLUE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_TAG_RED;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.lostandfound.model.ArticleList;
import seedu.lostandfound.model.article.Article;

/**
 * A utility class containing a list of {@code Article} objects to be used in tests.
 */
public class TypicalArticles {

    public static final String DEFAULT_OWNER = "Not Claimed";

    public static final Article BAG = new ArticleBuilder().withFinder("Alice Pauline").withOwner("Not Claimed")
            .withDescription("Found at 123, Jurong West Ave 6").withEmail("alice@example.com").withName("Nike Bag")
            .withPhone("94351253").withIsResolved(false).withTags("Black").build();
    public static final Article WALLET = new ArticleBuilder().withFinder("Benson Meier")
            .withDescription("Found at 311, Clementi Ave 2").withName("Adidas Wallet")
            .withEmail("johnd@example.com").withPhone("98765432").withOwner("Not Claimed")
            .withIsResolved(false).withTags("Black", "Sticker").build();
    public static final Article WATCH = new ArticleBuilder().withName("Casio Watch").withPhone("95352563")
            .withEmail("heinz@example.com").withIsResolved(false).withDescription("Found at wall street")
            .withOwner("Not Claimed").withFinder("Carl Kurz").build();
    public static final Article HEADPHONE = new ArticleBuilder().withName("AfterShockz Headphone").withPhone("87652533")
            .withEmail("cornelia@example.com").withDescription("Found at 10th street").withOwner("Not Claimed")
            .withFinder("Daniel Meier").withIsResolved(false).withTags("Blue").build();
    public static final Article LAPTOP = new ArticleBuilder().withFinder("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withDescription("Found at michegan ave").withIsResolved(false)
            .withName("Acer Aspire Laptop").withOwner("Not Claimed").build();
    public static final Article PHONE = new ArticleBuilder().withFinder("Fiona Kunz")
            .withIsResolved(false).withPhone("9482427").withName("IPhone XS").withOwner("Not Claimed")
            .withEmail("lydia@example.com").withDescription("Found at little tokyo").build();
    public static final Article CARD = new ArticleBuilder().withFinder("George Best").withOwner("Not Claimed")
            .withIsResolved(false).withPhone("9482442").withEmail("anna@example.com").withName("Matric Card")
            .withDescription("Found at 4th street").build();

    // Manually added
    public static final Article SHIRT = new ArticleBuilder().withName("Blue PayPal Shirt").withPhone("8482424")
            .withEmail("stefan@example.com").withDescription("Found at little india").withIsResolved(false)
            .withFinder("Hoon Meier").withOwner("Not Claimed").build();
    public static final Article NECKLACE = new ArticleBuilder().withName("Pandora Necklace").withPhone("8482131")
            .withEmail("hans@example.com").withDescription("Found at chicago ave")
            .withOwner("Not Claimed").withFinder("Ida Mueller").withIsResolved(false).build();

    // Manually added - Article's details found in {@code CommandTestUtil}
    public static final Article POWERBANK = new ArticleBuilder().withName(VALID_NAME_POWERBANK)
            .withPhone(VALID_PHONE_POWERBANK).withEmail(VALID_EMAIL_POWERBANK).withFinder(VALID_FINDER_POWERBANK)
            .withOwner(DEFAULT_OWNER).withDescription(VALID_DESCRIPTION_POWERBANK)
            .withIsResolved(false).withTags(VALID_TAG_RED).build();
    public static final Article MOUSE = new ArticleBuilder()
            .withName(VALID_NAME_MOUSE).withPhone(VALID_PHONE_MOUSE).withEmail(VALID_EMAIL_MOUSE)
            .withDescription(VALID_DESCRIPTION_MOUSE).withIsResolved(false).withOwner(DEFAULT_OWNER)
            .withTags(VALID_TAG_BLUE, VALID_TAG_RED).withFinder(VALID_FINDER_MOUSE).build();

    public static final String KEYWORD_MATCHING_MEIER = "-f Meier"; // A keyword that matches MEIER

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
        return new ArrayList<>(Arrays.asList(BAG, WALLET, WATCH, HEADPHONE, LAPTOP, PHONE, CARD));
    }
}
