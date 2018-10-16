package seedu.lostandfound.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.lostandfound.testutil.TypicalArticles.AMY;
import static seedu.lostandfound.testutil.TypicalArticles.BOB;
import static seedu.lostandfound.testutil.TypicalArticles.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.lostandfound.testutil.ArticleListBuilder;

public class VersionedArticleListTest {

    private final ReadOnlyArticleList articleListWithAmy = new ArticleListBuilder().withArticle(AMY).build();
    private final ReadOnlyArticleList articleListWithBob = new ArticleListBuilder().withArticle(BOB).build();
    private final ReadOnlyArticleList articleListWithCarl = new ArticleListBuilder().withArticle(CARL).build();
    private final ReadOnlyArticleList emptyArticleList = new ArticleListBuilder().build();

    @Test
    public void commit_singleArticleList_noStatesRemovedCurrentStateSaved() {
        VersionedArticleList versionedArticleList = prepareArticleListList(emptyArticleList);

        versionedArticleList.commit();
        assertArticleListListStatus(versionedArticleList,
                Collections.singletonList(emptyArticleList),
                emptyArticleList,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleArticleListPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedArticleList versionedArticleList = prepareArticleListList(
                emptyArticleList, articleListWithAmy, articleListWithBob);

        versionedArticleList.commit();
        assertArticleListListStatus(versionedArticleList,
                Arrays.asList(emptyArticleList, articleListWithAmy, articleListWithBob),
                articleListWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleArticleListPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedArticleList versionedArticleList = prepareArticleListList(
                emptyArticleList, articleListWithAmy, articleListWithBob);
        shiftCurrentStatePointerLeftwards(versionedArticleList, 2);

        versionedArticleList.commit();
        assertArticleListListStatus(versionedArticleList,
                Collections.singletonList(emptyArticleList),
                emptyArticleList,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleArticleListPointerAtEndOfStateList_returnsTrue() {
        VersionedArticleList versionedArticleList = prepareArticleListList(
                emptyArticleList, articleListWithAmy, articleListWithBob);

        assertTrue(versionedArticleList.canUndo());
    }

    @Test
    public void canUndo_multipleArticleListPointerAtStartOfStateList_returnsTrue() {
        VersionedArticleList versionedArticleList = prepareArticleListList(
                emptyArticleList, articleListWithAmy, articleListWithBob);
        shiftCurrentStatePointerLeftwards(versionedArticleList, 1);

        assertTrue(versionedArticleList.canUndo());
    }

    @Test
    public void canUndo_singleArticleList_returnsFalse() {
        VersionedArticleList versionedArticleList = prepareArticleListList(emptyArticleList);

        assertFalse(versionedArticleList.canUndo());
    }

    @Test
    public void canUndo_multipleArticleListPointerAtStartOfStateList_returnsFalse() {
        VersionedArticleList versionedArticleList = prepareArticleListList(
                emptyArticleList, articleListWithAmy, articleListWithBob);
        shiftCurrentStatePointerLeftwards(versionedArticleList, 2);

        assertFalse(versionedArticleList.canUndo());
    }

    @Test
    public void canRedo_multipleArticleListPointerNotAtEndOfStateList_returnsTrue() {
        VersionedArticleList versionedArticleList = prepareArticleListList(
                emptyArticleList, articleListWithAmy, articleListWithBob);
        shiftCurrentStatePointerLeftwards(versionedArticleList, 1);

        assertTrue(versionedArticleList.canRedo());
    }

    @Test
    public void canRedo_multipleArticleListPointerAtStartOfStateList_returnsTrue() {
        VersionedArticleList versionedArticleList = prepareArticleListList(
                emptyArticleList, articleListWithAmy, articleListWithBob);
        shiftCurrentStatePointerLeftwards(versionedArticleList, 2);

        assertTrue(versionedArticleList.canRedo());
    }

    @Test
    public void canRedo_singleArticleList_returnsFalse() {
        VersionedArticleList versionedArticleList = prepareArticleListList(emptyArticleList);

        assertFalse(versionedArticleList.canRedo());
    }

    @Test
    public void canRedo_multipleArticleListPointerAtEndOfStateList_returnsFalse() {
        VersionedArticleList versionedArticleList = prepareArticleListList(
                emptyArticleList, articleListWithAmy, articleListWithBob);

        assertFalse(versionedArticleList.canRedo());
    }

    @Test
    public void undo_multipleArticleListPointerAtEndOfStateList_success() {
        VersionedArticleList versionedArticleList = prepareArticleListList(
                emptyArticleList, articleListWithAmy, articleListWithBob);

        versionedArticleList.undo();
        assertArticleListListStatus(versionedArticleList,
                Collections.singletonList(emptyArticleList),
                articleListWithAmy,
                Collections.singletonList(articleListWithBob));
    }

    @Test
    public void undo_multipleArticleListPointerNotAtStartOfStateList_success() {
        VersionedArticleList versionedArticleList = prepareArticleListList(
                emptyArticleList, articleListWithAmy, articleListWithBob);
        shiftCurrentStatePointerLeftwards(versionedArticleList, 1);

        versionedArticleList.undo();
        assertArticleListListStatus(versionedArticleList,
                Collections.emptyList(),
                emptyArticleList,
                Arrays.asList(articleListWithAmy, articleListWithBob));
    }

    @Test
    public void undo_singleArticleList_throwsNoUndoableStateException() {
        VersionedArticleList versionedArticleList = prepareArticleListList(emptyArticleList);

        assertThrows(VersionedArticleList.NoUndoableStateException.class, versionedArticleList::undo);
    }

    @Test
    public void undo_multipleArticleListPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedArticleList versionedArticleList = prepareArticleListList(
                emptyArticleList, articleListWithAmy, articleListWithBob);
        shiftCurrentStatePointerLeftwards(versionedArticleList, 2);

        assertThrows(VersionedArticleList.NoUndoableStateException.class, versionedArticleList::undo);
    }

    @Test
    public void redo_multipleArticleListPointerNotAtEndOfStateList_success() {
        VersionedArticleList versionedArticleList = prepareArticleListList(
                emptyArticleList, articleListWithAmy, articleListWithBob);
        shiftCurrentStatePointerLeftwards(versionedArticleList, 1);

        versionedArticleList.redo();
        assertArticleListListStatus(versionedArticleList,
                Arrays.asList(emptyArticleList, articleListWithAmy),
                articleListWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleArticleListPointerAtStartOfStateList_success() {
        VersionedArticleList versionedArticleList = prepareArticleListList(
                emptyArticleList, articleListWithAmy, articleListWithBob);
        shiftCurrentStatePointerLeftwards(versionedArticleList, 2);

        versionedArticleList.redo();
        assertArticleListListStatus(versionedArticleList,
                Collections.singletonList(emptyArticleList),
                articleListWithAmy,
                Collections.singletonList(articleListWithBob));
    }

    @Test
    public void redo_singleArticleList_throwsNoRedoableStateException() {
        VersionedArticleList versionedArticleList = prepareArticleListList(emptyArticleList);

        assertThrows(VersionedArticleList.NoRedoableStateException.class, versionedArticleList::redo);
    }

    @Test
    public void redo_multipleArticleListPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedArticleList versionedArticleList = prepareArticleListList(
                emptyArticleList, articleListWithAmy, articleListWithBob);

        assertThrows(VersionedArticleList.NoRedoableStateException.class, versionedArticleList::redo);
    }

    @Test
    public void equals() {
        VersionedArticleList versionedArticleList = prepareArticleListList(articleListWithAmy, articleListWithBob);

        // same values -> returns true
        VersionedArticleList copy = prepareArticleListList(articleListWithAmy, articleListWithBob);
        assertTrue(versionedArticleList.equals(copy));

        // same object -> returns true
        assertTrue(versionedArticleList.equals(versionedArticleList));

        // null -> returns false
        assertFalse(versionedArticleList.equals(null));

        // different types -> returns false
        assertFalse(versionedArticleList.equals(1));

        // different state list -> returns false
        VersionedArticleList differentArticleListList = prepareArticleListList(articleListWithBob, articleListWithCarl);
        assertFalse(versionedArticleList.equals(differentArticleListList));

        // different current pointer index -> returns false
        VersionedArticleList differentCurrentStatePointer = prepareArticleListList(
                articleListWithAmy, articleListWithBob);
        shiftCurrentStatePointerLeftwards(versionedArticleList, 1);
        assertFalse(versionedArticleList.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedArticleList} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedArticleList#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedArticleList#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertArticleListListStatus(VersionedArticleList versionedArticleList,
                                             List<ReadOnlyArticleList> expectedStatesBeforePointer,
                                             ReadOnlyArticleList expectedCurrentState,
                                             List<ReadOnlyArticleList> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new ArticleList(versionedArticleList), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedArticleList.canUndo()) {
            versionedArticleList.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyArticleList expectedArticleList : expectedStatesBeforePointer) {
            assertEquals(expectedArticleList, new ArticleList(versionedArticleList));
            versionedArticleList.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyArticleList expectedArticleList : expectedStatesAfterPointer) {
            versionedArticleList.redo();
            assertEquals(expectedArticleList, new ArticleList(versionedArticleList));
        }

        // check that there are no more states after pointer
        assertFalse(versionedArticleList.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedArticleList.undo());
    }

    /**
     * Creates and returns a {@code VersionedArticleList} with the {@code articleListStates} added into it, and the
     * {@code VersionedArticleList#currentStatePointer} at the end of list.
     */
    private VersionedArticleList prepareArticleListList(ReadOnlyArticleList... articleListStates) {
        assertFalse(articleListStates.length == 0);

        VersionedArticleList versionedArticleList = new VersionedArticleList(articleListStates[0]);
        for (int i = 1; i < articleListStates.length; i++) {
            versionedArticleList.resetData(articleListStates[i]);
            versionedArticleList.commit();
        }

        return versionedArticleList;
    }

    /**
     * Shifts the {@code versionedArticleList#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedArticleList versionedArticleList, int count) {
        for (int i = 0; i < count; i++) {
            versionedArticleList.undo();
        }
    }
}
