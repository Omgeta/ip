package omega.command;

import omega.storage.Storage;
import omega.task.TaskList;
import omega.ui.Ui;

/**
 * Represents a command to list filtered tasks in the task list.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Constructs a FindCommand with the specified keyword.
     *
     * @param keyword Keyword to filter tasks by.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        TaskList matches = tasks.find(keyword);
        ui.showFindList(keyword, matches);
    }
}
