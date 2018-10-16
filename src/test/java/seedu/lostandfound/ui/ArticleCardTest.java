package seedu.lostandfound.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.lostandfound.ui.testutil.GuiTestAssert.assertCardDisplaysArticle;

import org.junit.Test;

import guitests.guihandles.ArticleCardHandle;
import seedu.lostandfound.model.article.Article;
import seedu.lostandfound.testutil.ArticleBuilder;

public class ArticleCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Article articleWithNoTags = new ArticleBuilder().withTags(new String[0]).build();
        ArticleCard articleCard = new ArticleCard(articleWithNoTags, 1);
        uiPartRule.setUiPart(articleCard);
        assertCardDisplay(articleCard, articleWithNoTags, 1);

        // with tags
        Article articleWithTags = new ArticleBuilder().build();
        articleCard = new ArticleCard(articleWithTags, 2);
        uiPartRule.setUiPart(articleCard);
        assertCardDisplay(articleCard, articleWithTags, 2);
    }

    @Test
    public void equals() {
        Article article = new ArticleBuilder().build();
        ArticleCard articleCard = new ArticleCard(article, 0);

        // same article, same index -> returns true
        ArticleCard copy = new ArticleCard(article, 0);
        assertTrue(articleCard.equals(copy));

        // same object -> returns true
        assertTrue(articleCard.equals(articleCard));

        // null -> returns false
        assertFalse(articleCard.equals(null));

        // different types -> returns false
        assertFalse(articleCard.equals(0));

        // different article, same index -> returns false
        Article differentArticle = new ArticleBuilder().withName("differentName").build();
        assertFalse(articleCard.equals(new ArticleCard(differentArticle, 0)));

        // same article, different index -> returns false
        assertFalse(articleCard.equals(new ArticleCard(article, 1)));
    }

    /**
     * Asserts that {@code articleCard} displays the details of {@code expectedArticle} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ArticleCard articleCard, Article expectedArticle, int expectedId) {
        guiRobot.pauseForHuman();

        ArticleCardHandle articleCardHandle = new ArticleCardHandle(articleCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", articleCardHandle.getId());

        // verify article details are displayed correctly
        assertCardDisplaysArticle(expectedArticle, articleCardHandle);
    }
}
