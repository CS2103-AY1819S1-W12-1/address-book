package seedu.lostandfound.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.lostandfound.testutil.TypicalArticles.getTypicalArticleList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.lostandfound.commons.events.model.ArticleListChangedEvent;
import seedu.lostandfound.commons.events.storage.DataSavingExceptionEvent;
import seedu.lostandfound.model.ArticleList;
import seedu.lostandfound.model.ReadOnlyArticleList;
import seedu.lostandfound.model.UserPrefs;
import seedu.lostandfound.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlArticleListStorage articleListStorage = new XmlArticleListStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(articleListStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.getRoot().toPath().resolve(fileName);
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void articleListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlArticleListStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlArticleListStorageTest} class.
         */
        ArticleList original = getTypicalArticleList();
        storageManager.saveArticleList(original);
        ReadOnlyArticleList retrieved = storageManager.readArticleList().get();
        assertEquals(original, new ArticleList(retrieved));
    }

    @Test
    public void getArticleListFilePath() {
        assertNotNull(storageManager.getArticleListFilePath());
    }

    @Test
    public void handleArticleListChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlArticleListStorageExceptionThrowingStub(Paths.get("dummy")),
                                             new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleArticleListChangedEvent(new ArticleListChangedEvent(new ArticleList()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlArticleListStorageExceptionThrowingStub extends XmlArticleListStorage {

        public XmlArticleListStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveArticleList(ReadOnlyArticleList articleList, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
