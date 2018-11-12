package seedu.lostandfound.model.article;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.lostandfound.logic.commands.CommandTestUtil.TRUE_ISRESOLVED;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_EMAIL_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_FINDER_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_NAME_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_OWNER_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_PHONE_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_TAG_BLUE;
import static seedu.lostandfound.testutil.TypicalArticles.BAG;
import static seedu.lostandfound.testutil.TypicalArticles.MOUSE;

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
        assertTrue(BAG.isSameArticle(BAG));

        // null -> returns false
        assertFalse(BAG.isSameArticle(null));

        // different name -> returns false
        Article editedAlice = new ArticleBuilder(BAG).withName(VALID_NAME_MOUSE).build();
        assertFalse(BAG.isSameArticle(editedAlice));

        // same name, same description, different phone and email -> returns true
        editedAlice = new ArticleBuilder(BAG).withPhone(VALID_PHONE_MOUSE).withEmail(VALID_EMAIL_MOUSE).build();
        assertTrue(BAG.isSameArticle(editedAlice));

        // same name, same description, different attributes -> returns true
        editedAlice = new ArticleBuilder(BAG).withEmail(VALID_EMAIL_MOUSE)
                .withFinder(VALID_FINDER_MOUSE).withTags(VALID_TAG_BLUE).build();
        assertTrue(BAG.isSameArticle(editedAlice));

        // same name, same description, different attributes -> returns true
        editedAlice = new ArticleBuilder(BAG).withPhone(VALID_PHONE_MOUSE)
                .withTags(VALID_TAG_BLUE).build();
        assertTrue(BAG.isSameArticle(editedAlice));

        // same name, same description, same email, different attributes -> returns true
        editedAlice = new ArticleBuilder(BAG).withTags(VALID_TAG_BLUE).withFinder(VALID_FINDER_MOUSE).build();
        assertTrue(BAG.isSameArticle(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Article aliceCopy = new ArticleBuilder(BAG).build();
        assertTrue(BAG.equals(aliceCopy));

        // same object -> returns true
        assertTrue(BAG.equals(BAG));

        // null -> returns false
        assertFalse(BAG.equals(null));

        // different type -> returns false
        assertFalse(BAG.equals(5));

        // different article -> returns false
        assertFalse(BAG.equals(MOUSE));

        // different name -> returns false
        Article editedAlice = new ArticleBuilder(BAG).withName(VALID_NAME_MOUSE).build();
        assertFalse(BAG.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new ArticleBuilder(BAG).withPhone(VALID_PHONE_MOUSE).build();
        assertFalse(BAG.equals(editedAlice));

        // different email -> returns false
        editedAlice = new ArticleBuilder(BAG).withEmail(VALID_EMAIL_MOUSE).build();
        assertFalse(BAG.equals(editedAlice));

        // different description -> returns false
        editedAlice = new ArticleBuilder(BAG).withDescription(VALID_DESCRIPTION_MOUSE).build();
        assertFalse(BAG.equals(editedAlice));

        // different finder -> returns false
        editedAlice = new ArticleBuilder(BAG).withFinder(VALID_FINDER_MOUSE).build();
        assertFalse(BAG.equals(editedAlice));

        // different owner -> returns false
        editedAlice = new ArticleBuilder(BAG).withOwner(VALID_OWNER_MOUSE).build();
        assertFalse(BAG.equals(editedAlice));

        // different isResolved -> returns false
        editedAlice = new ArticleBuilder(BAG).withIsResolved(TRUE_ISRESOLVED).build();
        assertFalse(BAG.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ArticleBuilder(BAG).withTags(VALID_TAG_BLUE).build();
        assertFalse(BAG.equals(editedAlice));
    }
}
