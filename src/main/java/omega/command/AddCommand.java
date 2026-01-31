package omega.command;

import omega.OmegaException;
import omega.storage.Storage;
import omega.task.Task;
import omega.task.TaskList;
import omega.ui.Ui;

/**
 * Represents a command to add a new task to the task list.
 */
public class AddCommand extends Command {
    private final Task taskToAdd;

    /**
     * Constructs an AddCommand with the specified task to add.
     *
     * @param taskToAdd The task to be added to the task list.
     */
    public AddCommand(Task taskToAdd) {
        this.taskToAdd = taskToAdd;
    }

    /**
     * Adds the task to the task list and saves the updated list to storage.
     *
     * @param tasks   Task list to add the new task to.
     * @param ui      User interface for displaying success message.
     * @param storage Storage system for saving the updated task list.
     * @return String response from command execution
     * @throws OmegaException If saving to storage fails
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws OmegaException {
        tasks.add(taskToAdd);
        storage.save(tasks);
        return ui.showAdded(taskToAdd, tasks.size());
    }
}
