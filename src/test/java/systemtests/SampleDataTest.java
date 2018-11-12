package systemtests;

import static seedu.lostandfound.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

import seedu.lostandfound.model.ArticleList;
import seedu.lostandfound.model.article.Article;
import seedu.lostandfound.model.util.SampleDataUtil;
import seedu.lostandfound.testutil.TestUtil;

public class SampleDataTest extends ArticleListSystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected ArticleList getInitialData() {
        return null;
    }

    /**
     * Returns a non-existent file location to force test app to load sample data.
     */
    @Override
    protected Path getDataFileLocation() {
        Path filePath = TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
        deleteFileIfExists(filePath);
        return filePath;
    }

    /**
     * Deletes the file at {@code filePath} if it exists.
     */
    private void deleteFileIfExists(Path filePath) {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException ioe) {
            throw new AssertionError(ioe);
        }
    }

    @Test
    public void articleList_dataFileDoesNotExist_loadSampleData() {
        Article[] expectedList = SampleDataUtil.getUnresolvedSampleArticles();
        assertListMatching(getArticleListPanel(), expectedList);
    }
}
