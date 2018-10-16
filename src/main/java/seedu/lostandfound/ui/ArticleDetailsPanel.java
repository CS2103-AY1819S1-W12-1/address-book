package seedu.lostandfound.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.lostandfound.commons.core.LogsCenter;
import seedu.lostandfound.commons.events.ui.ArticlePanelSelectionChangedEvent;
import seedu.lostandfound.model.article.Article;

/**
 * The Article Details Panel of the App.
 */
public class ArticleDetailsPanel extends UiPart<Region> {

    private static final String FXML = "ArticleDetailsPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label articleDetails;

    @FXML
    private FlowPane tags;

    @FXML
    private TextArea description;

    public ArticleDetailsPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     * Load article details into the panel.
     *
     * @param article The article to be loaded
     */
    private void loadArticleDetails(Article article) {
        Platform.runLater(() -> {
            articleDetails.setText(article.getName().fullName);
            tags.getChildren().clear();
            article.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
            description.setText(article.getDescription().value);
        });
    }

    @Subscribe
    private void handleArticlePanelSelectionChangedEvent(ArticlePanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadArticleDetails(event.getNewSelection());
    }
}
