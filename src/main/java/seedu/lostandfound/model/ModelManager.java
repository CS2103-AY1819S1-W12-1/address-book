package seedu.lostandfound.model;

import static java.util.Objects.requireNonNull;
import static seedu.lostandfound.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.lostandfound.commons.core.ComponentManager;
import seedu.lostandfound.commons.core.LogsCenter;
import seedu.lostandfound.commons.events.model.ArticleListChangedEvent;
import seedu.lostandfound.model.article.Article;

/**
 * Represents the in-memory model of the article list data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedArticleList versionedArticleList;
    private final FilteredList<Article> filteredArticles;

    /**
     * Initializes a ModelManager with the given articleList and userPrefs.
     */
    public ModelManager(ReadOnlyArticleList articleList, UserPrefs userPrefs) {
        super();
        requireAllNonNull(articleList, userPrefs);

        logger.fine("Initializing with article list: " + articleList + " and user prefs " + userPrefs);

        versionedArticleList = new VersionedArticleList(articleList);
        filteredArticles = new FilteredList<>(versionedArticleList.getArticleList());
    }

    public ModelManager() {
        this(new ArticleList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyArticleList newData) {
        versionedArticleList.resetData(newData);
        indicateArticleListChanged();
    }

    @Override
    public ReadOnlyArticleList getArticleList() {
        return versionedArticleList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateArticleListChanged() {
        raise(new ArticleListChangedEvent(versionedArticleList));
    }

    @Override
    public boolean hasArticle(Article article) {
        requireNonNull(article);
        return versionedArticleList.hasArticle(article);
    }

    @Override
    public void deleteArticle(Article target) {
        versionedArticleList.removeArticle(target);
        indicateArticleListChanged();
    }

    @Override
    public void addArticle(Article article) {
        versionedArticleList.addArticle(article);
        updateFilteredArticleList(PREDICATE_SHOW_ALL_ARTICLES);
        indicateArticleListChanged();
    }

    @Override
    public void updateArticle(Article target, Article editedArticle) {
        requireAllNonNull(target, editedArticle);

        versionedArticleList.updateArticle(target, editedArticle);
        indicateArticleListChanged();
    }

    //=========== Filtered Article List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Article} backed by the internal list of
     * {@code versionedArticleList}
     */
    @Override
    public ObservableList<Article> getFilteredArticleList() {
        return FXCollections.unmodifiableObservableList(filteredArticles);
    }

    @Override
    public void updateFilteredArticleList(Predicate<Article> predicate) {
        requireNonNull(predicate);
        filteredArticles.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoArticleList() {
        return versionedArticleList.canUndo();
    }

    @Override
    public boolean canRedoArticleList() {
        return versionedArticleList.canRedo();
    }

    @Override
    public void undoArticleList() {
        versionedArticleList.undo();
        indicateArticleListChanged();
    }

    @Override
    public void redoArticleList() {
        versionedArticleList.redo();
        indicateArticleListChanged();
    }

    @Override
    public void commitArticleList() {
        versionedArticleList.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedArticleList.equals(other.versionedArticleList)
                && filteredArticles.equals(other.filteredArticles);
    }

}
