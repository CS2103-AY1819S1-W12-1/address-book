package seedu.lostandfound.model.article;

import static java.util.Objects.requireNonNull;
import static seedu.lostandfound.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.lostandfound.model.article.exceptions.ArticleNotFoundException;
import seedu.lostandfound.model.article.exceptions.DuplicateArticleException;

/**
 * A list of articles that enforces uniqueness between its elements and does not allow nulls.
 * A article is considered unique by comparing using {@code Article#isSameArticle(Article)}. As such, adding and
 * updating of articles uses Article#isSameArticle(Article) for equality so as to ensure that the article being added
 * or updated is unique in terms of identity in the UniqueArticleList. However, the removal of a article uses
 * Article#equals(Object) so as to ensure that the article with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Article#isSameArticle(Article)
 */
public class UniqueArticleList implements Iterable<Article> {

    private final ObservableList<Article> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent article as the given argument.
     */
    public boolean contains(Article toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameArticle);
    }

    /**
     * Adds a article to the list.
     * The article must not already exist in the list.
     */
    public void add(Article toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateArticleException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the article {@code target} in the list with {@code editedArticle}.
     * {@code target} must exist in the list.
     * The article identity of {@code editedArticle} must not be the same as another existing article in the list.
     */
    public void setArticle(Article target, Article editedArticle) {
        requireAllNonNull(target, editedArticle);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ArticleNotFoundException();
        }

        if (!target.isSameArticle(editedArticle) && contains(editedArticle)) {
            throw new DuplicateArticleException();
        }

        internalList.set(index, editedArticle);
    }

    /**
     * Removes the equivalent article from the list.
     * The article must exist in the list.
     */
    public void remove(Article toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ArticleNotFoundException();
        }
    }

    public void setArticles(UniqueArticleList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code articles}.
     * {@code articles} must not contain duplicate articles.
     */
    public void setArticles(List<Article> articles) {
        requireAllNonNull(articles);
        if (!articlesAreUnique(articles)) {
            throw new DuplicateArticleException();
        }

        internalList.setAll(articles);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Article> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Article> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueArticleList // instanceof handles nulls
                        && internalList.equals(((UniqueArticleList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code articles} contains only unique articles.
     */
    private boolean articlesAreUnique(List<Article> articles) {
        for (int i = 0; i < articles.size() - 1; i++) {
            for (int j = i + 1; j < articles.size(); j++) {
                if (articles.get(i).isSameArticle(articles.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
