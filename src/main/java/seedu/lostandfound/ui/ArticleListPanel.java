package seedu.lostandfound.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.lostandfound.commons.core.LogsCenter;
import seedu.lostandfound.commons.events.ui.ArticlePanelSelectionChangedEvent;
import seedu.lostandfound.commons.events.ui.JumpToListRequestEvent;
import seedu.lostandfound.model.article.Article;

/**
 * Panel containing the list of articles.
 */
public class ArticleListPanel extends UiPart<Region> {
    private static final String FXML = "ArticleListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ArticleListPanel.class);

    @FXML
    private ListView<Article> articleListView;

    public ArticleListPanel(ObservableList<Article> articleList) {
        super(FXML);
        setConnections(articleList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Article> articleList) {
        articleListView.setItems(articleList);
        articleListView.setCellFactory(listView -> new ArticleListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        articleListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in article list panel changed to : '" + newValue + "'");
                        raise(new ArticlePanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code ArticleCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            articleListView.scrollTo(index);
            articleListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Article} using a {@code ArticleCard}.
     */
    class ArticleListViewCell extends ListCell<Article> {
        @Override
        protected void updateItem(Article article, boolean empty) {
            super.updateItem(article, empty);

            if (empty || article == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ArticleCard(article, getIndex() + 1).getRoot());
            }
        }
    }

}
