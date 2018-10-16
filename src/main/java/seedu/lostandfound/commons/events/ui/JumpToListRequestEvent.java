package seedu.lostandfound.commons.events.ui;

import seedu.lostandfound.commons.core.index.Index;
import seedu.lostandfound.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of articles
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
