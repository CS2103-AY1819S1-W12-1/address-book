package seedu.lostandfound.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.lostandfound.model.ArticleList;
import seedu.lostandfound.storage.XmlAdaptedArticle;
import seedu.lostandfound.storage.XmlAdaptedTag;
import seedu.lostandfound.storage.XmlSerializableArticleList;
import seedu.lostandfound.testutil.ArticleBuilder;
import seedu.lostandfound.testutil.ArticleListBuilder;
import seedu.lostandfound.testutil.TestUtil;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validArticleList.xml");
    private static final Path MISSING_ARTICLE_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingArticleField.xml");
    private static final Path INVALID_ARTICLE_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidArticleField.xml");
    private static final Path VALID_ARTICLE_FILE = TEST_DATA_FOLDER.resolve("validArticle.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempArticleList.xml");

    private static final String INVALID_PHONE = "9482asf424";

    private static final String VALID_NAME = "Hans Muster";
    private static final String VALID_PHONE = "9482424";
    private static final String VALID_EMAIL = "hans@example";
    private static final String VALID_DESCRIPTION = "4th street";
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("friends"));

    private static final boolean FALSE_ISRESOLVED = false;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, ArticleList.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, ArticleList.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, ArticleList.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        ArticleList dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableArticleList.class).toModelType();
        assertEquals(9, dataFromFile.getArticleList().size());
    }

    @Test
    public void xmlAdaptedArticleFromFile_fileWithMissingArticleField_validResult() throws Exception {
        XmlAdaptedArticle actualArticle = XmlUtil.getDataFromFile(
                MISSING_ARTICLE_FIELD_FILE, XmlAdaptedArticleWithRootElement.class);
        XmlAdaptedArticle expectedArticle = new XmlAdaptedArticle(
                null, VALID_PHONE, VALID_EMAIL, VALID_DESCRIPTION, FALSE_ISRESOLVED, VALID_TAGS);
        assertEquals(expectedArticle, actualArticle);
    }

    @Test
    public void xmlAdaptedArticleFromFile_fileWithInvalidArticleField_validResult() throws Exception {
        XmlAdaptedArticle actualArticle = XmlUtil.getDataFromFile(
                INVALID_ARTICLE_FIELD_FILE, XmlAdaptedArticleWithRootElement.class);
        XmlAdaptedArticle expectedArticle = new XmlAdaptedArticle(
                VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_DESCRIPTION, FALSE_ISRESOLVED, VALID_TAGS);
        assertEquals(expectedArticle, actualArticle);
    }

    @Test
    public void xmlAdaptedArticleFromFile_fileWithValidArticle_validResult() throws Exception {
        XmlAdaptedArticle actualArticle = XmlUtil.getDataFromFile(
                VALID_ARTICLE_FILE, XmlAdaptedArticleWithRootElement.class);
        XmlAdaptedArticle expectedArticle = new XmlAdaptedArticle(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_DESCRIPTION, FALSE_ISRESOLVED, VALID_TAGS);
        assertEquals(expectedArticle, actualArticle);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new ArticleList());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new ArticleList());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        XmlSerializableArticleList dataToWrite = new XmlSerializableArticleList(new ArticleList());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableArticleList dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableArticleList.class);
        assertEquals(dataToWrite, dataFromFile);

        ArticleListBuilder builder = new ArticleListBuilder(new ArticleList());
        dataToWrite = new XmlSerializableArticleList(
                builder.withArticle(new ArticleBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableArticleList.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedArticle}
     * objects.
     */
    @XmlRootElement(name = "article")
    private static class XmlAdaptedArticleWithRootElement extends XmlAdaptedArticle {}
}
