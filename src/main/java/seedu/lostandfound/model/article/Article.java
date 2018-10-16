package seedu.lostandfound.model.article;

import static seedu.lostandfound.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.lostandfound.model.tag.Tag;

/**
 * Represents a Article in the article list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Article {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Description description;
    private final Set<Tag> tags = new HashSet<>();

    // Others
    private final boolean isResolved;

    /**
     * Every field must be present and not null.
     */
    public Article(Name name, Phone phone, Email email, Description description, boolean isResolved, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, description, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.description = description;
        this.isResolved = isResolved;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Description getDescription() {
        return description;
    }

    public boolean getIsResolved() {
        return isResolved;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both articles of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two articles.
     */
    public boolean isSameArticle(Article otherArticle) {
        if (otherArticle == this) {
            return true;
        }

        return otherArticle != null
                && otherArticle.getName().equals(getName())
                && (otherArticle.getPhone().equals(getPhone()) || otherArticle.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both articles have the same identity and data fields.
     * This defines a stronger notion of equality between two articles.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Article)) {
            return false;
        }

        Article otherArticle = (Article) other;
        return otherArticle.getName().equals(getName())
                && otherArticle.getPhone().equals(getPhone())
                && otherArticle.getEmail().equals(getEmail())
                && otherArticle.getDescription().equals(getDescription())
                && otherArticle.getIsResolved() == getIsResolved()
                && otherArticle.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, description, isResolved, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Description: ")
                .append(getDescription())
                .append(" isResolved: ")
                .append(getIsResolved())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
