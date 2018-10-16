package guitests.guihandles;

import javafx.stage.Stage;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private final ArticleListPanelHandle articleListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;
    private final ArticleDetailsPanelHandle articleDetailsPanel;

    public MainWindowHandle(Stage stage) {
        super(stage);

        articleListPanel = new ArticleListPanelHandle(getChildNode(ArticleListPanelHandle.ARTICLE_LIST_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        articleDetailsPanel = new ArticleDetailsPanelHandle(getChildNode(ArticleDetailsPanelHandle.ARTICLE_DETAILS_ID));
    }

    public ArticleListPanelHandle getArticleListPanel() {
        return articleListPanel;
    }

    public ResultDisplayHandle getResultDisplay() {
        return resultDisplay;
    }

    public CommandBoxHandle getCommandBox() {
        return commandBox;
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return statusBarFooter;
    }

    public MainMenuHandle getMainMenu() {
        return mainMenu;
    }

    public ArticleDetailsPanelHandle getArticleDetailsPanel() {
        return articleDetailsPanel;
    }
}
