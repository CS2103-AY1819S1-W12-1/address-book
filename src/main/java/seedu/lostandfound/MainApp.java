package seedu.lostandfound;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.lostandfound.commons.core.Config;
import seedu.lostandfound.commons.core.EventsCenter;
import seedu.lostandfound.commons.core.LogsCenter;
import seedu.lostandfound.commons.core.Version;
import seedu.lostandfound.commons.events.ui.ExitAppRequestEvent;
import seedu.lostandfound.commons.exceptions.DataConversionException;
import seedu.lostandfound.commons.util.ConfigUtil;
import seedu.lostandfound.commons.util.StringUtil;
import seedu.lostandfound.logic.Logic;
import seedu.lostandfound.logic.LogicManager;
import seedu.lostandfound.model.ArticleList;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.ModelManager;
import seedu.lostandfound.model.ReadOnlyArticleList;
import seedu.lostandfound.model.UserPrefs;
import seedu.lostandfound.model.util.SampleDataUtil;
import seedu.lostandfound.storage.ArticleListStorage;
import seedu.lostandfound.storage.JsonUserPrefsStorage;
import seedu.lostandfound.storage.Storage;
import seedu.lostandfound.storage.StorageManager;
import seedu.lostandfound.storage.UserPrefsStorage;
import seedu.lostandfound.storage.XmlArticleListStorage;
import seedu.lostandfound.ui.Ui;
import seedu.lostandfound.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {
    public static final Version VERSION = new Version(0, 6, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing ArticleList ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        ArticleListStorage articleListStorage = new XmlArticleListStorage(userPrefs.getArticleListFilePath());
        storage = new StorageManager(articleListStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);
        model.updateFilteredArticleList(Model.NOT_RESOLVED_PREDICATE);
        logic = new LogicManager(model);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s article list and {@code userPrefs}. <br>
     * The data from the sample article list will be used instead if {@code storage}'s article list is not found,
     * or an empty article list will be used instead if errors occur when reading {@code storage}'s article list.
     */
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyArticleList> articleListOptional;
        ReadOnlyArticleList initialData;
        try {
            articleListOptional = storage.readArticleList();
            if (!articleListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample ArticleList");
            }
            initialData = articleListOptional.orElseGet(SampleDataUtil::getAllSampleArticleList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty ArticleList");
            initialData = new ArticleList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ArticleList");
            initialData = new ArticleList();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        // update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ArticleList");
            initializedPrefs = new UserPrefs();
        }

        // update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting ArticleList " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Article List ] =============================");
        ui.stop();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
        Platform.exit();
        System.exit(0);
    }

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
