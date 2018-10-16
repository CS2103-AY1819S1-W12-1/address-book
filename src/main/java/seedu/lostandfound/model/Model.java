package seedu.lostandfound.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.lostandfound.model.article.Article;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Article> PREDICATE_SHOW_ALL_ARTICLES = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyArticleList newData);

    /** Returns the ArticleList */
    ReadOnlyArticleList getArticleList();

    /**
     * Returns true if a article with the same identity as {@code article} exists in the article list.
     */
    boolean hasArticle(Article article);

    /**
     * Deletes the given article.
     * The article must exist in the article list.
     */
    void deleteArticle(Article target);

    /**
     * Adds the given article.
     * {@code article} must not already exist in the article list.
     */
    void addArticle(Article article);

    /**
     * Replaces the given article {@code target} with {@code editedArticle}.
     * {@code target} must exist in the article list.
     * The article identity of {@code editedArticle} must not be the same as another existing article in the description
     * book.
     */
    void updateArticle(Article target, Article editedArticle);

    /** Returns an unmodifiable view of the filtered article list */
    ObservableList<Article> getFilteredArticleList();

    /**
     * Updates the filter of the filtered article list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredArticleList(Predicate<Article> predicate);

    /**
     * Returns true if the model has previous article list states to restore.
     */
    boolean canUndoArticleList();

    /**
     * Returns true if the model has undone article list states to restore.
     */
    boolean canRedoArticleList();

    /**
     * Restores the model's article list to its previous state.
     */
    void undoArticleList();

    /**
     * Restores the model's article list to its previously undone state.
     */
    void redoArticleList();

    /**
     * Saves the current article list state for undo/redo.
     */
    void commitArticleList();
}
