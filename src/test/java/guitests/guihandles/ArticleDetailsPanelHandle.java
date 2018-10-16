package guitests.guihandles;

import javafx.scene.control.Label;

/**
 * A handler for the {@code ArticleDetailsPanel} of the UI
 */
public class ArticleDetailsPanelHandle extends NodeHandle<Label> {

    public static final String ARTICLE_DETAILS_ID = "#articleDetails";

    private String lastRememberedDetails;

    public ArticleDetailsPanelHandle(Label articleDetailsNode) {
        super(articleDetailsNode);
    }

    /**
     * Returns the loaded content in the article details.
     */
    public String getLoadedDetails() {
        return getRootNode().getText();
    }

    /**
     * Remembers the content of the currently loaded article details.
     */
    public void rememberDetails() {
        lastRememberedDetails = getLoadedDetails();
    }

    /**
     * Returns true if the current article details is different from the value remembered by the most recent
     * article details call.
     */
    public boolean isDetailsChanged() {
        return !lastRememberedDetails.equals(getLoadedDetails());
    }
}
