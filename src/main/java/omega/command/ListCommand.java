package omega.command;

import omega.storage.Storage;
import omega.task.TaskList;
import omega.ui.Ui;

/**
 * Represents a command to list all tasks in the task list.
 */
public class ListCommand extends Command {
    /**
     * Displays all tasks in the task list to the user.
     *
     * @param tasks   Task list containing the tasks to be listed.
     * @param ui      User interface to show the list of tasks.
     * @param storage Storage system (not used in this command).
     * @return String response from command execution
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return ui.showList(tasks);
    }
}
