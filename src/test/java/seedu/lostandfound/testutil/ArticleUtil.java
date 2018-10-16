package seedu.lostandfound.testutil;

import static seedu.lostandfound.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.lostandfound.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.lostandfound.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.lostandfound.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.lostandfound.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.lostandfound.logic.commands.AddCommand;
import seedu.lostandfound.logic.commands.EditCommand.EditArticleDescriptor;
import seedu.lostandfound.model.article.Article;
import seedu.lostandfound.model.tag.Tag;

/**
 * A utility class for Article.
 */
public class ArticleUtil {

    /**
     * Returns an add command string for adding the {@code article}.
     */
    public static String getAddCommand(Article article) {
        return AddCommand.COMMAND_WORD + " " + getArticleDetails(article);
    }

    /**
     * Returns the part of command string for the given {@code article}'s details.
     */
    public static String getArticleDetails(Article article) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + article.getName().fullName + " ");
        sb.append(PREFIX_PHONE + article.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + article.getEmail().value + " ");
        sb.append(PREFIX_DESCRIPTION + article.getDescription().value + " ");
        article.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditArticleDescriptor}'s details.
     */
    public static String getEditArticleDescriptorDetails(EditArticleDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getDescription().ifPresent(description ->
                sb.append(PREFIX_DESCRIPTION).append(description.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
