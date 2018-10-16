package seedu.lostandfound.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.lostandfound.commons.core.LogsCenter;
import seedu.lostandfound.commons.exceptions.DataConversionException;
import seedu.lostandfound.commons.exceptions.IllegalValueException;
import seedu.lostandfound.commons.util.FileUtil;
import seedu.lostandfound.model.ReadOnlyArticleList;

/**
 * A class to access ArticleList data stored as an xml file on the hard disk.
 */
public class XmlArticleListStorage implements ArticleListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlArticleListStorage.class);

    private Path filePath;

    public XmlArticleListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getArticleListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyArticleList> readArticleList() throws DataConversionException, IOException {
        return readArticleList(filePath);
    }

    /**
     * Similar to {@link #readArticleList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyArticleList> readArticleList(Path filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("ArticleList file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableArticleList xmlArticleList = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlArticleList.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveArticleList(ReadOnlyArticleList articleList) throws IOException {
        saveArticleList(articleList, filePath);
    }

    /**
     * Similar to {@link #saveArticleList(ReadOnlyArticleList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveArticleList(ReadOnlyArticleList articleList, Path filePath) throws IOException {
        requireNonNull(articleList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableArticleList(articleList));
    }

}
