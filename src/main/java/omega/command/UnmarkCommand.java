package omega.command;

import omega.OmegaException;
import omega.storage.Storage;
import omega.task.Task;
import omega.task.TaskList;
import omega.ui.Ui;

/**
 * Represents a command to unmark a task as not completed in the task list.
 */
public class UnmarkCommand extends Command {
    private final int index;

    /**
     * Constructs an UnmarkCommand with the specified task index.
     *
     * @param index Index of the task to be unmarked as not completed.
     */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    /**
     * Unmarks specified task, saves to storage and show unmarked message.
     *
     * @param tasks   Task list containing the task to be unmarked.
     * @param ui      User interface to show unmarked message.
     * @param storage Storage system for saving data.
     * @throws OmegaException If an error occurs during unmarking or saving.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws OmegaException {
        Task t = tasks.unmark(index);
        storage.save(tasks);
        ui.showUnmarked(t);
    }
}
