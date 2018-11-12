package seedu.lostandfound.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.lostandfound.commons.exceptions.IllegalValueException;
import seedu.lostandfound.commons.util.XmlUtil;
import seedu.lostandfound.model.ArticleList;
import seedu.lostandfound.testutil.TypicalArticles;

public class XmlSerializableArticleListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableArticleListTest");
    private static final Path TYPICAL_ARTICLES_FILE = TEST_DATA_FOLDER.resolve("typicalArticlesArticleList.xml");
    private static final Path INVALID_ARTICLE_FILE = TEST_DATA_FOLDER.resolve("invalidArticleArticleList.xml");
    private static final Path DUPLICATE_ARTICLE_FILE = TEST_DATA_FOLDER.resolve("duplicateArticleArticleList.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalArticlesFile_success() throws Exception {
        XmlSerializableArticleList dataFromFile = XmlUtil.getDataFromFile(TYPICAL_ARTICLES_FILE,
                XmlSerializableArticleList.class);
        ArticleList articleListFromFile = dataFromFile.toModelType();
        ArticleList typicalArticlesArticleList = TypicalArticles.getTypicalArticleList();
        assertEquals(articleListFromFile, typicalArticlesArticleList);
    }

    @Test
    public void toModelType_invalidArticleFile_throwsIllegalValueException() throws Exception {
        XmlSerializableArticleList dataFromFile = XmlUtil.getDataFromFile(INVALID_ARTICLE_FILE,
                XmlSerializableArticleList.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateArticles_throwsIllegalValueException() throws Exception {
        XmlSerializableArticleList dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_ARTICLE_FILE,
                XmlSerializableArticleList.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableArticleList.MESSAGE_DUPLICATE_ARTICLE);
        dataFromFile.toModelType();
    }

}
