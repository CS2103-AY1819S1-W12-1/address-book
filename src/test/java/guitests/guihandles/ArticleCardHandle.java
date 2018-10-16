package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.lostandfound.model.article.Article;

/**
 * Provides a handle to a article card in the article list panel.
 */
public class ArticleCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label descriptionLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final List<Label> tagLabels;

    public ArticleCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code article}.
     */
    public boolean equals(Article article) {
        return getName().equals(article.getName().fullName)
                && getDescription().equals(article.getDescription().value)
                && getPhone().equals(article.getPhone().value)
                && getEmail().equals(article.getEmail().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(article.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
