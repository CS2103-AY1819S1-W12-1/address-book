package seedu.lostandfound.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.lostandfound.commons.events.model.ArticleListChangedEvent;
import seedu.lostandfound.commons.events.storage.DataSavingExceptionEvent;
import seedu.lostandfound.commons.exceptions.DataConversionException;
import seedu.lostandfound.model.ReadOnlyArticleList;
import seedu.lostandfound.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ArticleListStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getArticleListFilePath();

    @Override
    Optional<ReadOnlyArticleList> readArticleList() throws DataConversionException, IOException;

    @Override
    void saveArticleList(ReadOnlyArticleList articleList) throws IOException;

    /**
     * Saves the current version of the Article List to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleArticleListChangedEvent(ArticleListChangedEvent abce);
}
