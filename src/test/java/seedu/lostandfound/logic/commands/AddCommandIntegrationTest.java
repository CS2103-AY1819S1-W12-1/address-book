package seedu.lostandfound.logic.commands;

import static seedu.lostandfound.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.lostandfound.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.lostandfound.testutil.TypicalArticles.getTypicalArticleList;

import org.junit.Before;
import org.junit.Test;

import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.ModelManager;
import seedu.lostandfound.model.UserPrefs;
import seedu.lostandfound.model.article.Article;
import seedu.lostandfound.testutil.ArticleBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalArticleList(), new UserPrefs());
    }

    @Test
    public void execute_newArticle_success() {
        Article validArticle = new ArticleBuilder().build();

        Model expectedModel = new ModelManager(model.getArticleList(), new UserPrefs());
        expectedModel.addArticle(validArticle);
        expectedModel.commitArticleList();

        assertCommandSuccess(new AddCommand(validArticle), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validArticle), expectedModel);
    }

    @Test
    public void execute_duplicateArticle_throwsCommandException() {
        Article articleInList = model.getArticleList().getArticleList().get(0);
        assertCommandFailure(new AddCommand(articleInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_ARTICLE);
    }

}
