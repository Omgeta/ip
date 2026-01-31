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

    /**
     * Displays filtered tasks in the task list to the user.
     *
     * @param tasks   Task list containing the tasks to be listed.
     * @param ui      User interface to show the list of tasks.
     * @param storage Storage system (not used in this command).
     * @return String response from command execution
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        TaskList matches = tasks.find(keyword);
        return ui.showFindList(keyword, matches);
    }
}
