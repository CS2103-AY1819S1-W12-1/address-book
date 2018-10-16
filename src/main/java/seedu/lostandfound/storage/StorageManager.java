package seedu.lostandfound.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.lostandfound.commons.core.ComponentManager;
import seedu.lostandfound.commons.core.LogsCenter;
import seedu.lostandfound.commons.events.model.ArticleListChangedEvent;
import seedu.lostandfound.commons.events.storage.DataSavingExceptionEvent;
import seedu.lostandfound.commons.exceptions.DataConversionException;
import seedu.lostandfound.model.ReadOnlyArticleList;
import seedu.lostandfound.model.UserPrefs;

/**
 * Manages storage of ArticleList data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ArticleListStorage articleListStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ArticleListStorage articleListStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.articleListStorage = articleListStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ ArticleList methods ==============================

    @Override
    public Path getArticleListFilePath() {
        return articleListStorage.getArticleListFilePath();
    }

    @Override
    public Optional<ReadOnlyArticleList> readArticleList() throws DataConversionException, IOException {
        return readArticleList(articleListStorage.getArticleListFilePath());
    }

    @Override
    public Optional<ReadOnlyArticleList> readArticleList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return articleListStorage.readArticleList(filePath);
    }

    @Override
    public void saveArticleList(ReadOnlyArticleList articleList) throws IOException {
        saveArticleList(articleList, articleListStorage.getArticleListFilePath());
    }

    @Override
    public void saveArticleList(ReadOnlyArticleList articleList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        articleListStorage.saveArticleList(articleList, filePath);
    }


    @Override
    @Subscribe
    public void handleArticleListChangedEvent(ArticleListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveArticleList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
