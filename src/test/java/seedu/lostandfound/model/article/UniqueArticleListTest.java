package seedu.lostandfound.model.article;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.lostandfound.testutil.TypicalArticles.ALICE;
import static seedu.lostandfound.testutil.TypicalArticles.BOB;

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
        assertFalse(uniqueArticleList.contains(ALICE));
    }

    @Test
    public void contains_articleInList_returnsTrue() {
        uniqueArticleList.add(ALICE);
        assertTrue(uniqueArticleList.contains(ALICE));
    }

    @Test
    public void contains_articleWithSameIdentityFieldsInList_returnsTrue() {
        uniqueArticleList.add(ALICE);
        Article editedAlice = new ArticleBuilder(ALICE)
                .withDescription(VALID_DESCRIPTION_BOB).withTags(VALID_TAG_HUSBAND)
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
        uniqueArticleList.add(ALICE);
        thrown.expect(DuplicateArticleException.class);
        uniqueArticleList.add(ALICE);
    }

    @Test
    public void setArticle_nullTargetArticle_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueArticleList.setArticle(null, ALICE);
    }

    @Test
    public void setArticle_nullEditedArticle_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueArticleList.setArticle(ALICE, null);
    }

    @Test
    public void setArticle_targetArticleNotInList_throwsArticleNotFoundException() {
        thrown.expect(ArticleNotFoundException.class);
        uniqueArticleList.setArticle(ALICE, ALICE);
    }

    @Test
    public void setArticle_editedArticleIsSameArticle_success() {
        uniqueArticleList.add(ALICE);
        uniqueArticleList.setArticle(ALICE, ALICE);
        UniqueArticleList expectedUniqueArticleList = new UniqueArticleList();
        expectedUniqueArticleList.add(ALICE);
        assertEquals(expectedUniqueArticleList, uniqueArticleList);
    }

    @Test
    public void setArticle_editedArticleHasSameIdentity_success() {
        uniqueArticleList.add(ALICE);
        Article editedAlice = new ArticleBuilder(ALICE)
                .withDescription(VALID_DESCRIPTION_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueArticleList.setArticle(ALICE, editedAlice);
        UniqueArticleList expectedUniqueArticleList = new UniqueArticleList();
        expectedUniqueArticleList.add(editedAlice);
        assertEquals(expectedUniqueArticleList, uniqueArticleList);
    }

    @Test
    public void setArticle_editedArticleHasDifferentIdentity_success() {
        uniqueArticleList.add(ALICE);
        uniqueArticleList.setArticle(ALICE, BOB);
        UniqueArticleList expectedUniqueArticleList = new UniqueArticleList();
        expectedUniqueArticleList.add(BOB);
        assertEquals(expectedUniqueArticleList, uniqueArticleList);
    }

    @Test
    public void setArticle_editedArticleHasNonUniqueIdentity_throwsDuplicateArticleException() {
        uniqueArticleList.add(ALICE);
        uniqueArticleList.add(BOB);
        thrown.expect(DuplicateArticleException.class);
        uniqueArticleList.setArticle(ALICE, BOB);
    }

    @Test
    public void remove_nullArticle_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueArticleList.remove(null);
    }

    @Test
    public void remove_articleDoesNotExist_throwsArticleNotFoundException() {
        thrown.expect(ArticleNotFoundException.class);
        uniqueArticleList.remove(ALICE);
    }

    @Test
    public void remove_existingArticle_removesArticle() {
        uniqueArticleList.add(ALICE);
        uniqueArticleList.remove(ALICE);
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
        uniqueArticleList.add(ALICE);
        UniqueArticleList expectedUniqueArticleList = new UniqueArticleList();
        expectedUniqueArticleList.add(BOB);
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
        uniqueArticleList.add(ALICE);
        List<Article> articleList = Collections.singletonList(BOB);
        uniqueArticleList.setArticles(articleList);
        UniqueArticleList expectedUniqueArticleList = new UniqueArticleList();
        expectedUniqueArticleList.add(BOB);
        assertEquals(expectedUniqueArticleList, uniqueArticleList);
    }

    @Test
    public void setArticles_listWithDuplicateArticles_throwsDuplicateArticleException() {
        List<Article> listWithDuplicateArticles = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateArticleException.class);
        uniqueArticleList.setArticles(listWithDuplicateArticles);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueArticleList.asUnmodifiableObservableList().remove(0);
    }
}
