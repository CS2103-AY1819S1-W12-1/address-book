package seedu.lostandfound.commons.events.model;

import seedu.lostandfound.commons.events.BaseEvent;
import seedu.lostandfound.model.ReadOnlyArticleList;

/** Indicates the ArticleList in the model has changed*/
public class ArticleListChangedEvent extends BaseEvent {

    public final ReadOnlyArticleList data;

    public ArticleListChangedEvent(ReadOnlyArticleList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of articles " + data.getArticleList().size();
    }
}
