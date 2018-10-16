package seedu.lostandfound.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.lostandfound.model.ArticleList;
import seedu.lostandfound.model.ReadOnlyArticleList;
import seedu.lostandfound.model.article.Article;
import seedu.lostandfound.model.article.Description;
import seedu.lostandfound.model.article.Email;
import seedu.lostandfound.model.article.Name;
import seedu.lostandfound.model.article.Phone;
import seedu.lostandfound.model.tag.Tag;

/**
 * Contains utility methods for populating {@code ArticleList} with sample data.
 */
public class SampleDataUtil {
    public static Article[] getSampleArticles() {
        return new Article[] {
            new Article(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Description("Blk 30 Geylang Street 29, #06-40"), Boolean.FALSE,
                getTagSet("friends")),
            new Article(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Description("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), Boolean.FALSE,
                getTagSet("colleagues", "friends")),
            new Article(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Description("Blk 11 Ang Mo Kio Street 74, #11-04"), Boolean.FALSE,
                getTagSet("neighbours")),
            new Article(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Description("Blk 436 Serangoon Gardens Street 26, #16-43"), Boolean.FALSE,
                getTagSet("family")),
            new Article(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Description("Blk 47 Tampines Street 20, #17-35"), Boolean.FALSE,
                getTagSet("classmates")),
            new Article(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Description("Blk 45 Aljunied Street 85, #11-31"), Boolean.FALSE,
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyArticleList getSampleArticleList() {
        ArticleList sampleAb = new ArticleList();
        for (Article sampleArticle : getSampleArticles()) {
            sampleAb.addArticle(sampleArticle);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
