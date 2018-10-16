package seedu.lostandfound.model.article;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.lostandfound.testutil.TypicalArticles.ALICE;
import static seedu.lostandfound.testutil.TypicalArticles.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.lostandfound.testutil.ArticleBuilder;

public class ArticleTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Article article = new ArticleBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        article.getTags().remove(0);
    }

    @Test
    public void isSameArticle() {
        // same object -> returns true
        assertTrue(ALICE.isSameArticle(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameArticle(null));

        // different phone and email -> returns false
        Article editedAlice = new ArticleBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameArticle(editedAlice));

        // different name -> returns false
        editedAlice = new ArticleBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameArticle(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new ArticleBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withDescription(VALID_DESCRIPTION_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameArticle(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new ArticleBuilder(ALICE).withPhone(VALID_PHONE_BOB).withDescription(VALID_DESCRIPTION_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameArticle(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new ArticleBuilder(ALICE)
                .withDescription(VALID_DESCRIPTION_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameArticle(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Article aliceCopy = new ArticleBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different article -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Article editedAlice = new ArticleBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new ArticleBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new ArticleBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different description -> returns false
        editedAlice = new ArticleBuilder(ALICE).withDescription(VALID_DESCRIPTION_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ArticleBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
