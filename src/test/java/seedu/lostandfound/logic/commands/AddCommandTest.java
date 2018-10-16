package seedu.lostandfound.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.lostandfound.logic.CommandHistory;
import seedu.lostandfound.logic.commands.exceptions.CommandException;
import seedu.lostandfound.model.ArticleList;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.ReadOnlyArticleList;
import seedu.lostandfound.model.article.Article;
import seedu.lostandfound.testutil.ArticleBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullArticle_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_articleAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingArticleAdded modelStub = new ModelStubAcceptingArticleAdded();
        Article validArticle = new ArticleBuilder().build();

        CommandResult commandResult = new AddCommand(validArticle).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validArticle), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validArticle), modelStub.articlesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateArticle_throwsCommandException() throws Exception {
        Article validArticle = new ArticleBuilder().build();
        AddCommand addCommand = new AddCommand(validArticle);
        ModelStub modelStub = new ModelStubWithArticle(validArticle);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_ARTICLE);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Article alice = new ArticleBuilder().withName("Alice").build();
        Article bob = new ArticleBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different article -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addArticle(Article article) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyArticleList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyArticleList getArticleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasArticle(Article article) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteArticle(Article target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateArticle(Article target, Article editedArticle) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Article> getFilteredArticleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredArticleList(Predicate<Article> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoArticleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoArticleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoArticleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoArticleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitArticleList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single article.
     */
    private class ModelStubWithArticle extends ModelStub {
        private final Article article;

        ModelStubWithArticle(Article article) {
            requireNonNull(article);
            this.article = article;
        }

        @Override
        public boolean hasArticle(Article article) {
            requireNonNull(article);
            return this.article.isSameArticle(article);
        }
    }

    /**
     * A Model stub that always accept the article being added.
     */
    private class ModelStubAcceptingArticleAdded extends ModelStub {
        final ArrayList<Article> articlesAdded = new ArrayList<>();

        @Override
        public boolean hasArticle(Article article) {
            requireNonNull(article);
            return articlesAdded.stream().anyMatch(article::isSameArticle);
        }

        @Override
        public void addArticle(Article article) {
            requireNonNull(article);
            articlesAdded.add(article);
        }

        @Override
        public void commitArticleList() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyArticleList getArticleList() {
            return new ArticleList();
        }
    }

}
