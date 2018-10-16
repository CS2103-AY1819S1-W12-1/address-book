package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.lostandfound.model.article.Article;

/**
 * Provides a handle for {@code ArticleListPanel} containing the list of {@code ArticleCard}.
 */
public class ArticleListPanelHandle extends NodeHandle<ListView<Article>> {
    public static final String ARTICLE_LIST_VIEW_ID = "#articleListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Article> lastRememberedSelectedArticleCard;

    public ArticleListPanelHandle(ListView<Article> articleListPanelNode) {
        super(articleListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code ArticleCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public ArticleCardHandle getHandleToSelectedCard() {
        List<Article> selectedArticleList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedArticleList.size() != 1) {
            throw new AssertionError("Article list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(ArticleCardHandle::new)
                .filter(handle -> handle.equals(selectedArticleList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<Article> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code article}.
     */
    public void navigateToCard(Article article) {
        if (!getRootNode().getItems().contains(article)) {
            throw new IllegalArgumentException("Article does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(article);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code ArticleCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the article card handle of a article associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public ArticleCardHandle getArticleCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(ArticleCardHandle::new)
                .filter(handle -> handle.equals(getArticle(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Article getArticle(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code ArticleCard} in the list.
     */
    public void rememberSelectedArticleCard() {
        List<Article> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedArticleCard = Optional.empty();
        } else {
            lastRememberedSelectedArticleCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code ArticleCard} is different from the value remembered by the most recent
     * {@code rememberSelectedArticleCard()} call.
     */
    public boolean isSelectedArticleCardChanged() {
        List<Article> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedArticleCard.isPresent();
        } else {
            return !lastRememberedSelectedArticleCard.isPresent()
                    || !lastRememberedSelectedArticleCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
