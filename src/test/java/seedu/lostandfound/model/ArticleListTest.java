package seedu.lostandfound.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_DESCRIPTION_POWERBANK;
import static seedu.lostandfound.logic.commands.CommandTestUtil.VALID_TAG_BLUE;
import static seedu.lostandfound.testutil.TypicalArticles.BAG;
import static seedu.lostandfound.testutil.TypicalArticles.getTypicalArticleList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.lostandfound.model.article.Article;
import seedu.lostandfound.model.article.exceptions.DuplicateArticleException;
import seedu.lostandfound.testutil.ArticleBuilder;

public class ArticleListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ArticleList articleList = new ArticleList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), articleList.getArticleList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        articleList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyArticleList_replacesData() {
        ArticleList newData = getTypicalArticleList();
        articleList.resetData(newData);
        assertEquals(newData, articleList);
    }

    @Test
    public void resetData_withDuplicateArticles_throwsDuplicateArticleException() {
        // Two articles with the same identity fields
        Article editedAlice = new ArticleBuilder(BAG)
                .withDescription(VALID_DESCRIPTION_POWERBANK).withTags(VALID_TAG_BLUE)
                .build();
        List<Article> newArticles = Arrays.asList(BAG, editedAlice);
        ArticleListStub newData = new ArticleListStub(newArticles);

        thrown.expect(DuplicateArticleException.class);
        articleList.resetData(newData);
    }

    @Test
    public void hasArticle_nullArticle_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        articleList.hasArticle(null);
    }

    @Test
    public void hasArticle_articleNotInArticleList_returnsFalse() {
        assertFalse(articleList.hasArticle(BAG));
    }

    @Test
    public void hasArticle_articleInArticleList_returnsTrue() {
        articleList.addArticle(BAG);
        assertTrue(articleList.hasArticle(BAG));
    }

    @Test
    public void hasArticle_articleWithSameIdentityFieldsInArticleList_returnsTrue() {
        articleList.addArticle(BAG);
        Article editedAlice = new ArticleBuilder(BAG)
                .withDescription(VALID_DESCRIPTION_POWERBANK).withTags(VALID_TAG_BLUE)
                .build();
        assertTrue(articleList.hasArticle(editedAlice));
    }

    @Test
    public void getArticleList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        articleList.getArticleList().remove(0);
    }

    /**
     * A stub ReadOnlyArticleList whose articles list can violate interface constraints.
     */
    private static class ArticleListStub implements ReadOnlyArticleList {
        private final ObservableList<Article> articles = FXCollections.observableArrayList();

        ArticleListStub(Collection<Article> articles) {
            this.articles.setAll(articles);
        }

        @Override
        public ObservableList<Article> getArticleList() {
            return articles;
        }
    }

}
