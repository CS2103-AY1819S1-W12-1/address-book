package seedu.lostandfound.model.article;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_FINDER_MOUSE;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_TAG_BLUE;
import static seedu.lostandfound.testutil.TypicalArticles.BAG;
import static seedu.lostandfound.testutil.TypicalArticles.MOUSE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.lostandfound.model.article.exceptions.ArticleNotFoundException;
import seedu.lostandfound.model.article.exceptions.DuplicateArticleException;
import seedu.lostandfound.testutil.ArticleBuilder;

public class UniqueArticleListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueArticleList uniqueArticleList = new UniqueArticleList();

    @Test
    public void contains_nullArticle_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueArticleList.contains(null);
    }

    @Test
    public void contains_articleNotInList_returnsFalse() {
        assertFalse(uniqueArticleList.contains(BAG));
    }

    @Test
    public void contains_articleInList_returnsTrue() {
        uniqueArticleList.add(BAG);
        assertTrue(uniqueArticleList.contains(BAG));
    }

    @Test
    public void contains_articleWithSameIdentityFieldsInList_returnsTrue() {
        uniqueArticleList.add(BAG);
        Article editedAlice = new ArticleBuilder(BAG)
                .withFinder(VALID_FINDER_MOUSE).withTags(VALID_TAG_BLUE)
                .build();
        assertTrue(uniqueArticleList.contains(editedAlice));
    }

    @Test
    public void add_nullArticle_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueArticleList.add(null);
    }

    @Test
    public void add_duplicateArticle_throwsDuplicateArticleException() {
        uniqueArticleList.add(BAG);
        thrown.expect(DuplicateArticleException.class);
        uniqueArticleList.add(BAG);
    }

    @Test
    public void setArticle_nullTargetArticle_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueArticleList.setArticle(null, BAG);
    }

    @Test
    public void setArticle_nullEditedArticle_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueArticleList.setArticle(BAG, null);
    }

    @Test
    public void setArticle_targetArticleNotInList_throwsArticleNotFoundException() {
        thrown.expect(ArticleNotFoundException.class);
        uniqueArticleList.setArticle(BAG, BAG);
    }

    @Test
    public void setArticle_editedArticleIsSameArticle_success() {
        uniqueArticleList.add(BAG);
        uniqueArticleList.setArticle(BAG, BAG);
        UniqueArticleList expectedUniqueArticleList = new UniqueArticleList();
        expectedUniqueArticleList.add(BAG);
        assertEquals(expectedUniqueArticleList, uniqueArticleList);
    }

    @Test
    public void setArticle_editedArticleHasSameIdentity_success() {
        uniqueArticleList.add(BAG);
        Article editedAlice = new ArticleBuilder(BAG)
                .withFinder(VALID_FINDER_MOUSE).withTags(VALID_TAG_BLUE)
                .build();
        uniqueArticleList.setArticle(BAG, editedAlice);
        UniqueArticleList expectedUniqueArticleList = new UniqueArticleList();
        expectedUniqueArticleList.add(editedAlice);
        assertEquals(expectedUniqueArticleList, uniqueArticleList);
    }

    @Test
    public void setArticle_editedArticleHasDifferentIdentity_success() {
        uniqueArticleList.add(BAG);
        uniqueArticleList.setArticle(BAG, MOUSE);
        UniqueArticleList expectedUniqueArticleList = new UniqueArticleList();
        expectedUniqueArticleList.add(MOUSE);
        assertEquals(expectedUniqueArticleList, uniqueArticleList);
    }

    @Test
    public void setArticle_editedArticleHasNonUniqueIdentity_throwsDuplicateArticleException() {
        uniqueArticleList.add(BAG);
        uniqueArticleList.add(MOUSE);
        thrown.expect(DuplicateArticleException.class);
        uniqueArticleList.setArticle(BAG, MOUSE);
    }

    @Test
    public void remove_nullArticle_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueArticleList.remove(null);
    }

    @Test
    public void remove_articleDoesNotExist_throwsArticleNotFoundException() {
        thrown.expect(ArticleNotFoundException.class);
        uniqueArticleList.remove(BAG);
    }

    @Test
    public void remove_existingArticle_removesArticle() {
        uniqueArticleList.add(BAG);
        uniqueArticleList.remove(BAG);
        UniqueArticleList expectedUniqueArticleList = new UniqueArticleList();
        assertEquals(expectedUniqueArticleList, uniqueArticleList);
    }

    @Test
    public void setArticles_nullUniqueArticleList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueArticleList.setArticles((UniqueArticleList) null);
    }

    @Test
    public void setArticles_uniqueArticleList_replacesOwnListWithProvidedUniqueArticleList() {
        uniqueArticleList.add(BAG);
        UniqueArticleList expectedUniqueArticleList = new UniqueArticleList();
        expectedUniqueArticleList.add(MOUSE);
        uniqueArticleList.setArticles(expectedUniqueArticleList);
        assertEquals(expectedUniqueArticleList, uniqueArticleList);
    }

    @Test
    public void setArticles_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueArticleList.setArticles((List<Article>) null);
    }

    @Test
    public void setArticles_list_replacesOwnListWithProvidedList() {
        uniqueArticleList.add(BAG);
        List<Article> articleList = Collections.singletonList(MOUSE);
        uniqueArticleList.setArticles(articleList);
        UniqueArticleList expectedUniqueArticleList = new UniqueArticleList();
        expectedUniqueArticleList.add(MOUSE);
        assertEquals(expectedUniqueArticleList, uniqueArticleList);
    }

    @Test
    public void setArticles_listWithDuplicateArticles_throwsDuplicateArticleException() {
        List<Article> listWithDuplicateArticles = Arrays.asList(BAG, BAG);
        thrown.expect(DuplicateArticleException.class);
        uniqueArticleList.setArticles(listWithDuplicateArticles);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueArticleList.asUnmodifiableObservableList().remove(0);
    }
}
