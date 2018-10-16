package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.lostandfound.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.lostandfound.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.lostandfound.ui.testutil.GuiTestAssert.assertListMatching;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import guitests.guihandles.ArticleDetailsPanelHandle;
import guitests.guihandles.ArticleListPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import seedu.lostandfound.TestApp;
import seedu.lostandfound.commons.core.EventsCenter;
import seedu.lostandfound.commons.core.index.Index;
import seedu.lostandfound.logic.commands.ClearCommand;
import seedu.lostandfound.logic.commands.FindCommand;
import seedu.lostandfound.logic.commands.ListCommand;
import seedu.lostandfound.logic.commands.SelectCommand;
import seedu.lostandfound.model.ArticleList;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.testutil.TypicalArticles;
import seedu.lostandfound.ui.CommandBox;

/**
 * A system test class for ArticleList, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class ArticleListSystemTest {
    @ClassRule
    public static ClockRule clockRule = new ClockRule();

    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("text-input", "text-field", CommandBox.ERROR_STYLE_CLASS);

    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;
    private SystemTestSetupHelper setupHelper;

    @BeforeClass
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initialize();
    }

    @Before
    public void setUp() {
        setupHelper = new SystemTestSetupHelper();
        testApp = setupHelper.setupApplication(this::getInitialData, getDataFileLocation());
        mainWindowHandle = setupHelper.setupMainWindowHandle();

        assertApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() {
        setupHelper.tearDownStage();
        EventsCenter.clearSubscribers();
    }

    /**
     * Returns the data to be loaded into the file in {@link #getDataFileLocation()}.
     */
    protected ArticleList getInitialData() {
        return TypicalArticles.getTypicalArticleList();
    }

    /**
     * Returns the directory of the data file.
     */
    protected Path getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    public MainWindowHandle getMainWindowHandle() {
        return mainWindowHandle;
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public ArticleListPanelHandle getArticleListPanel() {
        return mainWindowHandle.getArticleListPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public ArticleDetailsPanelHandle getArticleDetailsPanel() {
        return mainWindowHandle.getArticleDetailsPanel();
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return mainWindowHandle.getStatusBarFooter();
    }

    public ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    /**
     * Executes {@code command} in the application's {@code CommandBox}.
     * Method returns after UI components have been updated.
     */
    protected void executeCommand(String command) {
        rememberStates();
        // Injects a fixed clock before executing a command so that the time stamp shown in the status bar
        // after each command is predictable and also different from the previous command.
        clockRule.setInjectedClockToCurrentTime();

        mainWindowHandle.getCommandBox().run(command);
    }

    /**
     * Displays all articles in the article list.
     */
    protected void showAllArticles() {
        executeCommand(ListCommand.COMMAND_WORD);
        assertEquals(getModel().getArticleList().getArticleList().size(), getModel().getFilteredArticleList().size());
    }

    /**
     * Displays all articles with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showArticlesWithName(String keyword) {
        executeCommand(FindCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredArticleList().size() < getModel().getArticleList().getArticleList().size());
    }

    /**
     * Selects the article at {@code index} of the displayed list.
     */
    protected void selectArticle(Index index) {
        executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getArticleListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all articles in the article list.
     */
    protected void deleteAllArticles() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getArticleList().getArticleList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same article objects as {@code expectedModel}
     * and the article list panel displays the articles in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
            Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new ArticleList(expectedModel.getArticleList()), testApp.readStorageArticleList());
        assertListMatching(getArticleListPanel(), expectedModel.getFilteredArticleList());
    }

    /**
     * Calls {@code ArticleDetailsPanelHandle}, {@code ArticleListPanelHandle} and
     * {@code StatusBarFooterHandle} to remember their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        getArticleDetailsPanel().rememberDetails();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getArticleListPanel().rememberSelectedArticleCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the article detail's content remains displaying
     * the detail of the previously selected article.
     * @see ArticleDetailsPanelHandle#isDetailsChanged()
     */
    protected void assertSelectedCardDeselected() {
        assertFalse(getArticleDetailsPanel().isDetailsChanged());
        assertFalse(getArticleListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the article detail's content is changed to display the details of the article in the article list
     * panel at {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see ArticleDetailsPanelHandle#isDetailsChanged()
     * @see ArticleListPanelHandle#isSelectedArticleCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        getArticleListPanel().navigateToCard(getArticleListPanel().getSelectedCardIndex());
        String selectedCardName = getArticleListPanel().getHandleToSelectedCard().getName();
        String expectedDetails = selectedCardName;
        assertEquals(expectedDetails, getArticleDetailsPanel().getLoadedDetails());

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getArticleListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the article detail's content and the selected card in the article list panel remain unchanged.
     * @see ArticleDetailsPanelHandle#isDetailsChanged()
     * @see ArticleListPanelHandle#isSelectedArticleCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        assertFalse(getArticleDetailsPanel().isDetailsChanged());
        assertFalse(getArticleListPanel().isSelectedArticleCardChanged());
    }

    /**
     * Asserts that the command box's shows the default style.
     */
    protected void assertCommandBoxShowsDefaultStyle() {
        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the command box's shows the error style.
     */
    protected void assertCommandBoxShowsErrorStyle() {
        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the entire status bar remains the same.
     */
    protected void assertStatusBarUnchanged() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        assertFalse(handle.isSaveLocationChanged());
        assertFalse(handle.isSyncStatusChanged());
    }

    /**
     * Asserts that only the sync status in the status bar was changed to the timing of
     * {@code ClockRule#getInjectedClock()}, while the save location remains the same.
     */
    protected void assertStatusBarUnchangedExceptSyncStatus() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        String timestamp = new Date(clockRule.getInjectedClock().millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, handle.getSyncStatus());
        assertFalse(handle.isSaveLocationChanged());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        assertEquals("", getCommandBox().getInput());
        assertEquals("", getResultDisplay().getText());
        assertEquals("", getArticleDetailsPanel().getLoadedDetails());
        assertListMatching(getArticleListPanel(), getModel().getFilteredArticleList());
        assertEquals(Paths.get(".").resolve(testApp.getStorageSaveLocation()).toString(),
                getStatusBarFooter().getSaveLocation());
        assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}
