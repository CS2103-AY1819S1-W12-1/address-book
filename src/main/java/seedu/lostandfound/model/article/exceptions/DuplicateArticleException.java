package seedu.lostandfound.model.article.exceptions;

/**
 * Signals that the operation will result in duplicate Articles (Articles are considered duplicates if they have the
 * same identity).
 */
public class DuplicateArticleException extends RuntimeException {
    public DuplicateArticleException() {
        super("Operation would result in duplicate articles");
    }
}
