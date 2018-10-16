package seedu.lostandfound.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.lostandfound.testutil.EventsUtil.postNow;
import static seedu.lostandfound.testutil.TypicalArticles.getTypicalArticles;
import static seedu.lostandfound.testutil.TypicalIndexes.INDEX_SECOND_ARTICLE;
import static seedu.lostandfound.ui.testutil.GuiTestAssert.assertCardDisplaysArticle;
import static seedu.lostandfound.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.ArticleCardHandle;
import guitests.guihandles.ArticleListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.lostandfound.commons.events.ui.JumpToListRequestEvent;
import seedu.lostandfound.commons.util.FileUtil;
import seedu.lostandfound.commons.util.XmlUtil;
import seedu.lostandfound.model.article.Article;
import seedu.lostandfound.storage.XmlSerializableArticleList;

public class ArticleListPanelTest extends GuiUnitTest {
    private static final ObservableList<Article> TYPICAL_ARTICLES =
            FXCollections.observableList(getTypicalArticles());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_ARTICLE);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private ArticleListPanelHandle articleListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_ARTICLES);

        for (int i = 0; i < TYPICAL_ARTICLES.size(); i++) {
            articleListPanelHandle.navigateToCard(TYPICAL_ARTICLES.get(i));
            Article expectedArticle = TYPICAL_ARTICLES.get(i);
            ArticleCardHandle actualCard = articleListPanelHandle.getArticleCardHandle(i);

            assertCardDisplaysArticle(expectedArticle, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_ARTICLES);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        ArticleCardHandle expectedArticle = articleListPanelHandle
                .getArticleCardHandle(INDEX_SECOND_ARTICLE.getZeroBased());
        ArticleCardHandle selectedArticle = articleListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedArticle, selectedArticle);
    }

    /**
     * Verifies that creating and deleting large number of articles in {@code ArticleListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Article> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of article cards exceeded time limit");
    }

    /**
     * Returns a list of articles containing {@code articleCount} articles that is used to populate the
     * {@code ArticleListPanel}.
     */
    private ObservableList<Article> createBackingList(int articleCount) throws Exception {
        Path xmlFile = createXmlFileWithArticles(articleCount);
        XmlSerializableArticleList xmlArticleList =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableArticleList.class);
        return FXCollections.observableArrayList(xmlArticleList.toModelType().getArticleList());
    }

    /**
     * Returns a .xml file containing {@code articleCount} articles. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithArticles(int articleCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<articlelist>\n");
        for (int i = 0; i < articleCount; i++) {
            builder.append("<articles>\n");
            builder.append("<name>").append(i).append("a</name>\n");
            builder.append("<phone>000</phone>\n");
            builder.append("<email>a@aa</email>\n");
            builder.append("<description>a</description>\n");
            builder.append("</articles>\n");
        }
        builder.append("</articlelist>\n");

        Path manyArticlesFile = Paths.get(TEST_DATA_FOLDER + "manyArticles.xml");
        FileUtil.createFile(manyArticlesFile);
        FileUtil.writeToFile(manyArticlesFile, builder.toString());
        manyArticlesFile.toFile().deleteOnExit();
        return manyArticlesFile;
    }

    /**
     * Initializes {@code articleListPanelHandle} with a {@code ArticleListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code ArticleListPanel}.
     */
    private void initUi(ObservableList<Article> backingList) {
        ArticleListPanel articleListPanel = new ArticleListPanel(backingList);
        uiPartRule.setUiPart(articleListPanel);

        articleListPanelHandle = new ArticleListPanelHandle(getChildNode(articleListPanel.getRoot(),
                ArticleListPanelHandle.ARTICLE_LIST_VIEW_ID));
    }
}
