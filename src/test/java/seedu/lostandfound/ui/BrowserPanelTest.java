package seedu.lostandfound.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.lostandfound.testutil.EventsUtil.postNow;
import static seedu.lostandfound.testutil.TypicalArticles.ALICE;
import static seedu.lostandfound.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.lostandfound.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.lostandfound.MainApp;
import seedu.lostandfound.commons.events.ui.ArticlePanelSelectionChangedEvent;

public class BrowserPanelTest extends GuiUnitTest {
    private ArticlePanelSelectionChangedEvent selectionChangedEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new ArticlePanelSelectionChangedEvent(ALICE);

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a article
        postNow(selectionChangedEventStub);
        URL expectedArticleUrl = new URL(BrowserPanel.SEARCH_PAGE_URL
                + ALICE.getName().fullName.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedArticleUrl, browserPanelHandle.getLoadedUrl());
    }
}