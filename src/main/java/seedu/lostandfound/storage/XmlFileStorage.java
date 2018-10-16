package seedu.lostandfound.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.lostandfound.commons.exceptions.DataConversionException;
import seedu.lostandfound.commons.util.XmlUtil;

/**
 * Stores articlelist data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given articlelist data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableArticleList articleList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, articleList);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns article list in the file or an empty article list
     */
    public static XmlSerializableArticleList loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableArticleList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
