package seedu.lostandfound.model.image;

import static java.util.Objects.requireNonNull;
import static seedu.lostandfound.commons.util.AppUtil.checkArgument;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import seedu.lostandfound.commons.core.LogsCenter;
import seedu.lostandfound.commons.util.FileUtil;
import seedu.lostandfound.model.util.Sequence;
import seedu.lostandfound.storage.StorageManager;

/**
 * Represents a Article's image in the article list.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Image {
    public static final String MESSAGE_CONSTRAINTS = "Path should be valid";

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private static final String VALIDATION_REGEX = "(0|[1-9][0-9]*)\\.(png|svg|jpg|jpeg)";
    private static final Sequence SEQUENCE = Sequence.getInstance();
    private static final Path IMAGE_FOLDER = Paths.get("data", "images");

    public final String filename;

    private Path path;
    private String basename;
    private Integer id;

    /**
     * Constructs a {@code Image}.
     *
     * @param file a valid image file. will set a reference to that path.
     */
    public Image(String file) throws InvalidPathException {
        logger.fine("Trying to set file as image");
        requireNonNull(file);
        checkArgument(isValid(file), MESSAGE_CONSTRAINTS);
        logger.fine("File is valid");
        path = Paths.get(file);
        filename = FileUtil.getFilename(path);
        basename = FileUtil.getBasename(path);
        id = Integer.parseInt(basename);
        SEQUENCE.set(id);
    }

    /**
     * Constructs a {@code Image}
     *
     * @param file a valid image file. will set a reference to that path.
     */
    public Image(Path file) throws InvalidPathException {
        this(file.toString());
    }

    public static Image create(String pathName) throws IOException {
        return Image.create(Paths.get(pathName));
    }

    /**
     * Create image from a path and import it to our own data library.
     * @param path The original image path
     * @return an image from such path copied to our path
     * @throws IOException
     */
    public static Image create(Path path) throws IOException {
        Path target = Paths.get(IMAGE_FOLDER.toString() ,
                SEQUENCE.next().toString() + "." + FileUtil.getExtension(path));
        FileUtil.copy(path, target);
        return new Image(target);
    }

    /**
     * Returns true if the given string is a valid image path.
     */
    public static boolean isValid(String test) {
        if (test == null) {
            return false;
        }

        if (!FileUtil.isValidPath(test)) {
            return false;
        }

        if (!FileUtil.isFileExists(Paths.get(test))) {
            return false;
        }

        return Paths.get(test).getFileName().toString().matches(VALIDATION_REGEX);
    }

    public static Image getDefault() {
        return new Image("data/images/0.png");
    }

    @Override
    public String toString() {
        return path.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Image // instanceof handles nulls
                && path.equals(((Image) other).path)); // state check
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }
}
