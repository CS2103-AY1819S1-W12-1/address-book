package seedu.lostandfound.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.lostandfound.model.Model.PREDICATE_SHOW_ALL_ARTICLES;
import static seedu.lostandfound.testutil.TypicalArticles.ALICE;
import static seedu.lostandfound.testutil.TypicalArticles.BENSON;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.lostandfound.model.article.NameContainsKeywordsPredicate;
import seedu.lostandfound.testutil.ArticleListBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasArticle_nullArticle_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasArticle(null);
    }

    @Test
    public void hasArticle_articleNotInArticleList_returnsFalse() {
        assertFalse(modelManager.hasArticle(ALICE));
    }

    @Test
    public void hasArticle_articleInArticleList_returnsTrue() {
        modelManager.addArticle(ALICE);
        assertTrue(modelManager.hasArticle(ALICE));
    }

    @Test
    public void getFilteredArticleList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredArticleList().remove(0);
    }

    @Test
    public void equals() {
        ArticleList articleList = new ArticleListBuilder().withArticle(ALICE).withArticle(BENSON).build();
        ArticleList differentArticleList = new ArticleList();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(articleList, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(articleList, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different articleList -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentArticleList, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredArticleList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(articleList, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredArticleList(PREDICATE_SHOW_ALL_ARTICLES);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setArticleListFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(articleList, differentUserPrefs)));
    }
}
