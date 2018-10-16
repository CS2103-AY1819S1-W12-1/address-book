package seedu.lostandfound.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.ArticleCardHandle;
import guitests.guihandles.ArticleListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.lostandfound.model.article.Article;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(ArticleCardHandle expectedCard, ArticleCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getDescription(), actualCard.getDescription());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedArticle}.
     */
    public static void assertCardDisplaysArticle(Article expectedArticle, ArticleCardHandle actualCard) {
        assertEquals(expectedArticle.getName().fullName, actualCard.getName());
        assertEquals(expectedArticle.getPhone().value, actualCard.getPhone());
        assertEquals(expectedArticle.getEmail().value, actualCard.getEmail());
        assertEquals(expectedArticle.getDescription().value, actualCard.getDescription());
        assertEquals(expectedArticle.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code articleListPanelHandle} displays the details of {@code articles} correctly and
     * in the correct order.
     */
    public static void assertListMatching(ArticleListPanelHandle articleListPanelHandle, Article... articles) {
        for (int i = 0; i < articles.length; i++) {
            articleListPanelHandle.navigateToCard(i);
            assertCardDisplaysArticle(articles[i], articleListPanelHandle.getArticleCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code articleListPanelHandle} displays the details of {@code articles} correctly and
     * in the correct order.
     */
    public static void assertListMatching(ArticleListPanelHandle articleListPanelHandle, List<Article> articles) {
        assertListMatching(articleListPanelHandle, articles.toArray(new Article[0]));
    }

    /**
     * Asserts the size of the list in {@code articleListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(ArticleListPanelHandle articleListPanelHandle, int size) {
        int numberOfPeople = articleListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
