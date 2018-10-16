package seedu.lostandfound.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.lostandfound.model.article.Article;
import seedu.lostandfound.model.article.UniqueArticleList;

/**
 * Wraps all data at the description-book level
 * Duplicates are not allowed (by .isSameArticle comparison)
 */
public class ArticleList implements ReadOnlyArticleList {

    private final UniqueArticleList articles;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        articles = new UniqueArticleList();
    }

    public ArticleList() {}

    /**
     * Creates an ArticleList using the Articles in the {@code toBeCopied}
     */
    public ArticleList(ReadOnlyArticleList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the article list with {@code articles}.
     * {@code articles} must not contain duplicate articles.
     */
    public void setArticles(List<Article> articles) {
        this.articles.setArticles(articles);
    }

    /**
     * Resets the existing data of this {@code ArticleList} with {@code newData}.
     */
    public void resetData(ReadOnlyArticleList newData) {
        requireNonNull(newData);

        setArticles(newData.getArticleList());
    }

    //// article-level operations

    /**
     * Returns true if a article with the same identity as {@code article} exists in the article list.
     */
    public boolean hasArticle(Article article) {
        requireNonNull(article);
        return articles.contains(article);
    }

    /**
     * Adds a article to the article list.
     * The article must not already exist in the article list.
     */
    public void addArticle(Article p) {
        articles.add(p);
    }

    /**
     * Replaces the given article {@code target} in the list with {@code editedArticle}.
     * {@code target} must exist in the article list.
     * The article identity of {@code editedArticle} must not be the same as another existing article in the description
     * book.
     */
    public void updateArticle(Article target, Article editedArticle) {
        requireNonNull(editedArticle);

        articles.setArticle(target, editedArticle);
    }

    /**
     * Removes {@code key} from this {@code ArticleList}.
     * {@code key} must exist in the article list.
     */
    public void removeArticle(Article key) {
        articles.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return articles.asUnmodifiableObservableList().size() + " articles";
        // TODO: refine later
    }

    @Override
    public ObservableList<Article> getArticleList() {
        return articles.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ArticleList // instanceof handles nulls
                && articles.equals(((ArticleList) other).articles));
    }

    @Override
    public int hashCode() {
        return articles.hashCode();
    }
}
