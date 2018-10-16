package seedu.lostandfound.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.lostandfound.testutil.TypicalArticles.ALICE;
import static seedu.lostandfound.testutil.TypicalArticles.HOON;
import static seedu.lostandfound.testutil.TypicalArticles.IDA;
import static seedu.lostandfound.testutil.TypicalArticles.getTypicalArticleList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.lostandfound.commons.exceptions.DataConversionException;
import seedu.lostandfound.model.ArticleList;
import seedu.lostandfound.model.ReadOnlyArticleList;

public class XmlArticleListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlArticleListStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readArticleList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readArticleList(null);
    }

    private java.util.Optional<ReadOnlyArticleList> readArticleList(String filePath) throws Exception {
        return new XmlArticleListStorage(Paths.get(filePath)).readArticleList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readArticleList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readArticleList("NotXmlFormatArticleList.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readArticleList_invalidArticleArticleList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readArticleList("invalidArticleArticleList.xml");
    }

    @Test
    public void readArticleList_invalidAndValidArticleArticleList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readArticleList("invalidAndValidArticleArticleList.xml");
    }

    @Test
    public void readAndSaveArticleList_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempArticleList.xml");
        ArticleList original = getTypicalArticleList();
        XmlArticleListStorage xmlArticleListStorage = new XmlArticleListStorage(filePath);

        //Save in new file and read back
        xmlArticleListStorage.saveArticleList(original, filePath);
        ReadOnlyArticleList readBack = xmlArticleListStorage.readArticleList(filePath).get();
        assertEquals(original, new ArticleList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addArticle(HOON);
        original.removeArticle(ALICE);
        xmlArticleListStorage.saveArticleList(original, filePath);
        readBack = xmlArticleListStorage.readArticleList(filePath).get();
        assertEquals(original, new ArticleList(readBack));

        //Save and read without specifying file path
        original.addArticle(IDA);
        xmlArticleListStorage.saveArticleList(original); //file path not specified
        readBack = xmlArticleListStorage.readArticleList().get(); //file path not specified
        assertEquals(original, new ArticleList(readBack));

    }

    @Test
    public void saveArticleList_nullArticleList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveArticleList(null, "SomeFile.xml");
    }

    /**
     * Saves {@code articleList} at the specified {@code filePath}.
     */
    private void saveArticleList(ReadOnlyArticleList articleList, String filePath) {
        try {
            new XmlArticleListStorage(Paths.get(filePath))
                    .saveArticleList(articleList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveArticleList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveArticleList(new ArticleList(), null);
    }


}
