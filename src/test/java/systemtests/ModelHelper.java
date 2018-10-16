package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.article.Article;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Article> PREDICATE_MATCHING_NO_ARTICLES = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Article> toDisplay) {
        Optional<Predicate<Article>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredArticleList(predicate.orElse(PREDICATE_MATCHING_NO_ARTICLES));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Article... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Article} equals to {@code other}.
     */
    private static Predicate<Article> getPredicateMatching(Article other) {
        return article -> article.equals(other);
    }
}
