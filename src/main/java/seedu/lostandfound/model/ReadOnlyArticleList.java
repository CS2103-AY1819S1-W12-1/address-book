package seedu.lostandfound.model;

import javafx.collections.ObservableList;
import seedu.lostandfound.model.article.Article;

/**
 * Unmodifiable view of an article list
 */
public interface ReadOnlyArticleList {

    /**
     * Returns an unmodifiable view of the articles list.
     * This list will not contain any duplicate articles.
     */
    ObservableList<Article> getArticleList();

}
