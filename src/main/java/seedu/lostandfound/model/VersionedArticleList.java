package seedu.lostandfound.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code ArticleList} that keeps track of its own history.
 */
public class VersionedArticleList extends ArticleList {

    private final List<ReadOnlyArticleList> articleListStateList;
    private int currentStatePointer;

    public VersionedArticleList(ReadOnlyArticleList initialState) {
        super(initialState);

        articleListStateList = new ArrayList<>();
        articleListStateList.add(new ArticleList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code ArticleList} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        articleListStateList.add(new ArticleList(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        articleListStateList.subList(currentStatePointer + 1, articleListStateList.size()).clear();
    }

    /**
     * Restores the article list to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(articleListStateList.get(currentStatePointer));
    }

    /**
     * Restores the article list to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(articleListStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has article list states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has article list states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < articleListStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedArticleList)) {
            return false;
        }

        VersionedArticleList otherVersionedArticleList = (VersionedArticleList) other;

        // state check
        return super.equals(otherVersionedArticleList)
                && articleListStateList.equals(otherVersionedArticleList.articleListStateList)
                && currentStatePointer == otherVersionedArticleList.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of articleListState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of articleListState list, unable to redo.");
        }
    }
}
