package seedu.lostandfound.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.lostandfound.commons.core.ComponentManager;
import seedu.lostandfound.commons.core.LogsCenter;
import seedu.lostandfound.logic.commands.Command;
import seedu.lostandfound.logic.commands.CommandResult;
import seedu.lostandfound.logic.commands.exceptions.CommandException;
import seedu.lostandfound.logic.parser.ArticleListParser;
import seedu.lostandfound.logic.parser.exceptions.ParseException;
import seedu.lostandfound.model.Model;
import seedu.lostandfound.model.article.Article;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final ArticleListParser articleListParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        articleListParser = new ArticleListParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = articleListParser.parseCommand(commandText);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Article> getFilteredArticleList() {
        return model.getFilteredArticleList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
