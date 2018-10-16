package seedu.lostandfound.testutil;

import seedu.lostandfound.model.ArticleList;
import seedu.lostandfound.model.article.Article;

/**
 * A utility class to help with building Articlelist objects.
 * Example usage: <br>
 *     {@code ArticleList ab = new ArticleListBuilder().withArticle("John", "Doe").build();}
 */
public class ArticleListBuilder {

    private ArticleList articleList;

    public ArticleListBuilder() {
        articleList = new ArticleList();
    }

    public ArticleListBuilder(ArticleList articleList) {
        this.articleList = articleList;
    }

    /**
     * Adds a new {@code Article} to the {@code ArticleList} that we are building.
     */
    public ArticleListBuilder withArticle(Article article) {
        articleList.addArticle(article);
        return this;
    }

    public ArticleList build() {
        return articleList;
    }
}
