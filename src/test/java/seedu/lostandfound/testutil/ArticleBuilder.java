package seedu.lostandfound.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.lostandfound.model.article.Article;
import seedu.lostandfound.model.article.Description;
import seedu.lostandfound.model.article.Email;
import seedu.lostandfound.model.article.Name;
import seedu.lostandfound.model.article.Phone;
import seedu.lostandfound.model.tag.Tag;
import seedu.lostandfound.model.util.SampleDataUtil;

/**
 * A utility class to help with building Article objects.
 */
public class ArticleBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_DESCRIPTION = "123, Jurong West Ave 6, #08-111";
    public static final boolean DEFAULT_ISRESOLVED = false;

    private Name name;
    private Phone phone;
    private Email email;
    private Description description;
    private boolean isResolved;
    private Set<Tag> tags;

    public ArticleBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        description = new Description(DEFAULT_DESCRIPTION);
        isResolved = DEFAULT_ISRESOLVED;
        tags = new HashSet<>();
    }

    /**
     * Initializes the ArticleBuilder with the data of {@code articleToCopy}.
     */
    public ArticleBuilder(Article articleToCopy) {
        name = articleToCopy.getName();
        phone = articleToCopy.getPhone();
        email = articleToCopy.getEmail();
        description = articleToCopy.getDescription();
        isResolved = articleToCopy.getIsResolved();
        tags = new HashSet<>(articleToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Article} that we are building.
     */
    public ArticleBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Article} that we are building.
     */
    public ArticleBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Article} that we are building.
     */
    public ArticleBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Article} that we are building.
     */
    public ArticleBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code isResolved} of the {@code Article} that we are building.
     */
    public ArticleBuilder withIsResolved(boolean isResolved) {
        this.isResolved = false;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Article} that we are building.
     */
    public ArticleBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Article build() {
        return new Article(name, phone, email, description, isResolved, tags);
    }

}
