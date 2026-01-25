package omega.command;

import omega.OmegaException;
import omega.storage.Storage;
import omega.task.Task;
import omega.task.TaskList;
import omega.ui.Ui;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private final int index;

    /**
     * Constructs a DeleteCommand with the specified task index.
     *
     * @param index Index of the task to be deleted.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Deletes specified task from list, saves to storage and show removal message.
     * 
     * @param tasks   Task list from which the task will be deleted.
     * @param ui      User interface to show removal message.
     * @param storage Storage system for saving data.
     * @throws OmegaException If an error occurs during deletion or saving.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws OmegaException {
        Task t = tasks.delete(index);
        storage.save(tasks);
        ui.showDeleted(t, tasks.size());
    }
}
