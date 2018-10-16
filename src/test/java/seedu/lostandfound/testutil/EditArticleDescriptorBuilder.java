package seedu.lostandfound.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.lostandfound.logic.commands.EditCommand.EditArticleDescriptor;
import seedu.lostandfound.model.article.Article;
import seedu.lostandfound.model.article.Description;
import seedu.lostandfound.model.article.Email;
import seedu.lostandfound.model.article.Name;
import seedu.lostandfound.model.article.Phone;
import seedu.lostandfound.model.tag.Tag;

/**
 * A utility class to help with building EditArticleDescriptor objects.
 */
public class EditArticleDescriptorBuilder {

    private EditArticleDescriptor descriptor;

    public EditArticleDescriptorBuilder() {
        descriptor = new EditArticleDescriptor();
    }

    public EditArticleDescriptorBuilder(EditArticleDescriptor descriptor) {
        this.descriptor = new EditArticleDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditArticleDescriptor} with fields containing {@code article}'s details
     */
    public EditArticleDescriptorBuilder(Article article) {
        descriptor = new EditArticleDescriptor();
        descriptor.setName(article.getName());
        descriptor.setPhone(article.getPhone());
        descriptor.setEmail(article.getEmail());
        descriptor.setDescription(article.getDescription());
        descriptor.setTags(article.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditArticleDescriptor} that we are building.
     */
    public EditArticleDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditArticleDescriptor} that we are building.
     */
    public EditArticleDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditArticleDescriptor} that we are building.
     */
    public EditArticleDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditArticleDescriptor} that we are building.
     */
    public EditArticleDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditArticleDescriptor}
     * that we are building.
     */
    public EditArticleDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditArticleDescriptor build() {
        return descriptor;
    }
}
