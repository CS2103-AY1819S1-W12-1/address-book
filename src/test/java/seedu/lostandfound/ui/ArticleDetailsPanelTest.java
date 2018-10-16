package seedu.lostandfound.ui;

import static org.junit.Assert.assertEquals;
import static seedu.lostandfound.testutil.EventsUtil.postNow;
import static seedu.lostandfound.testutil.TypicalArticles.ALICE;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ArticleDetailsPanelHandle;
import seedu.lostandfound.commons.events.ui.ArticlePanelSelectionChangedEvent;

public class ArticleDetailsPanelTest extends GuiUnitTest {
    private ArticlePanelSelectionChangedEvent selectionChangedEventStub;

    private ArticleDetailsPanel articleDetailsPanel;
    private ArticleDetailsPanelHandle articleDetailsPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new ArticlePanelSelectionChangedEvent(ALICE);

        guiRobot.interact(() -> articleDetailsPanel = new ArticleDetailsPanel());
        uiPartRule.setUiPart(articleDetailsPanel);

        articleDetailsPanelHandle = new ArticleDetailsPanelHandle(getChildNode(articleDetailsPanel.getRoot(),
                ArticleDetailsPanelHandle.ARTICLE_DETAILS_ID));
    }

    @Test
    public void display() {
        // default article details
        guiRobot.pauseForHuman();
        assertEquals("", articleDetailsPanelHandle.getLoadedDetails());

        // associated details of an article
        postNow(selectionChangedEventStub);
        String expectedArticleDetails = ALICE.getName().fullName;

        assertEquals(expectedArticleDetails, articleDetailsPanelHandle.getLoadedDetails());
    }
}
