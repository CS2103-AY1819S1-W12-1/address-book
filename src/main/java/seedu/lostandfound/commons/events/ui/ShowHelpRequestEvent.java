package seedu.lostandfound.commons.events.ui;

import seedu.lostandfound.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
 */
public class ShowHelpRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
