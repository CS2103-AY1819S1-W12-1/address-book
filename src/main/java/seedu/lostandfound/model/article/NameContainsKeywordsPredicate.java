package seedu.lostandfound.model.article;

import java.util.List;
import java.util.function.Predicate;

import seedu.lostandfound.commons.util.StringUtil;

/**
 * Tests that a {@code Article}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Article> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Article article) {
        return keywords.stream() //changed to AND operation instead of OR
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(article.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
