package seedu.lostandfound.commons.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;

import seedu.lostandfound.commons.core.LogsCenter;
import seedu.lostandfound.storage.StorageManager;

/**
 * Writes and reads files
 */
public class FileUtil {


    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private static final String CHARSET = "UTF-8";

    public static boolean isFileExists(Path file) {
        return Files.exists(file) && Files.isRegularFile(file);
    }

    /**
     * Returns true if {@code path} can be converted into a {@code Path} via
     * {@link java.nio.file.Paths#get(String, String...)}, otherwise returns false.
     * @param path A string representing the file path. Cannot be null.
     */
    public static boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException ipe) {
            return false;
        }
        return true;
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     * @throws IOException if the file or directory cannot be created.
     */
    public static void createIfMissing(Path file) throws IOException {
        if (!isFileExists(file)) {
            createFile(file);
        }
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     */
    public static void createFile(Path file) throws IOException {
        if (Files.exists(file)) {
            return;
        }

        createParentDirsOfFile(file);

        Files.createFile(file);
    }

    /**
     * Creates parent directories of file if it has a parent directory
     */
    public static void createParentDirsOfFile(Path file) throws IOException {
        Path parentDir = file.getParent();

        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }
    }

    /**
     * Assumes file exists
     */
    public static String readFromFile(Path file) throws IOException {
        return new String(Files.readAllBytes(file), CHARSET);
    }

    /**
     * Writes given string to a file.
     * Will create the file if it does not exist yet.
     */
    public static void writeToFile(Path file, String content) throws IOException {
        Files.write(file, content.getBytes(CHARSET));
    }

    /**
     * Copying file from source to target.
     * @param source the source file
     * @param target the target path
     * @throws IOException
     */
    public static void copy(Path source, Path target) throws IOException {
        logger.fine("Trying to copy from " + source.toString() + " path to " + target.toString());
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        logger.fine("File copying succeeded");
    }

    public static String getFilename(Path path) {
        return path.getFileName().toString();
    }

    public static String getBasename(Path path) {
        String[] split = getFilename(path).split("\\.");
        return split[0];
    }

    public static String getExtension(Path path) {
        String[] split = getFilename(path).split("\\.");
        if (split.length == 2) {
            return split[1];
        }
        return "";
    }
}
