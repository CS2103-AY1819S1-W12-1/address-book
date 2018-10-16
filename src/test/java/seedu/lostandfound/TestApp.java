package seedu.lostandfound;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.lostandfound.commons.core.Config;
import seedu.lostandfound.commons.core.GuiSettings;
import seedu.lostandfound.commons.exceptions.DataConversionException;
import seedu.lostandfound.commons.util.FileUtil;
import seedu.lostandfound.commons.util.XmlUtil;
import seedu.lostandfound.model.ArticleList;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.ModelManager;
import seedu.lostandfound.model.ReadOnlyArticleList;
import seedu.lostandfound.model.UserPrefs;
import seedu.lostandfound.storage.UserPrefsStorage;
import seedu.lostandfound.storage.XmlSerializableArticleList;
import seedu.lostandfound.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.xml");
    public static final String APP_TITLE = "Test App";

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<ReadOnlyArticleList> initialDataSupplier = () -> null;
    protected Path saveFileLocation = SAVE_LOCATION_FOR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyArticleList> initialDataSupplier, Path saveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableArticleList(this.initialDataSupplier.get()),
                    this.saveFileLocation);
        }
    }

    @Override
    protected Config initConfig(Path configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setAppTitle(APP_TITLE);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        UserPrefs userPrefs = super.initPrefs(storage);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.updateLastUsedGuiSetting(new GuiSettings(600.0, 600.0, (int) x, (int) y));
        userPrefs.setArticleListFilePath(saveFileLocation);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the article list data stored inside the storage file.
     */
    public ArticleList readStorageArticleList() {
        try {
            return new ArticleList(storage.readArticleList().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the ArticleList format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns the file path of the storage file.
     */
    public Path getStorageSaveLocation() {
        return storage.getArticleListFilePath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager((model.getArticleList()), new UserPrefs());
        ModelHelper.setFilteredList(copy, model.getFilteredArticleList());
        return copy;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates an XML file at the {@code filePath} with the {@code data}.
     */
    private <T> void createDataFileWithData(T data, Path filePath) {
        try {
            FileUtil.createIfMissing(filePath);
            XmlUtil.saveDataToFile(filePath, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
