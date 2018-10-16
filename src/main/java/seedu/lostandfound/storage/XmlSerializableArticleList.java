package seedu.lostandfound.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.lostandfound.commons.exceptions.IllegalValueException;
import seedu.lostandfound.model.ArticleList;
import seedu.lostandfound.model.ReadOnlyArticleList;
import seedu.lostandfound.model.article.Article;

/**
 * An Immutable ArticleList that is serializable to XML format
 */
@XmlRootElement(name = "articlelist")
public class XmlSerializableArticleList {

    public static final String MESSAGE_DUPLICATE_ARTICLE = "Articles list contains duplicate article(s).";

    @XmlElement
    private List<XmlAdaptedArticle> articles;

    /**
     * Creates an empty XmlSerializableArticleList.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableArticleList() {
        articles = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableArticleList(ReadOnlyArticleList src) {
        this();
        articles.addAll(src.getArticleList().stream().map(XmlAdaptedArticle::new).collect(Collectors.toList()));
    }

    /**
     * Converts this articlelist into the model's {@code ArticleList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedArticle}.
     */
    public ArticleList toModelType() throws IllegalValueException {
        ArticleList articleList = new ArticleList();
        for (XmlAdaptedArticle p : articles) {
            Article article = p.toModelType();
            if (articleList.hasArticle(article)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ARTICLE);
            }
            articleList.addArticle(article);
        }
        return articleList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableArticleList)) {
            return false;
        }
        return articles.equals(((XmlSerializableArticleList) other).articles);
    }
}
