package omega.command;

import omega.OmegaException;
import omega.storage.Storage;
import omega.task.Task;
import omega.task.TaskList;
import omega.ui.Ui;

/**
 * Represents a command to mark a task as completed in the task list.
 */
public class MarkCommand extends Command {
    private final int index;

    /**
     * Constructs a MarkCommand with the specified task index.
     *
     * @param index Index of the task to be marked as completed.
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    /**
     * Marks specified task as completed, saves to storage and show marked message.
     *
     * @param tasks   Task list containing the task to be marked.
     * @param ui      User interface to show marked message.
     * @param storage Storage system for saving data.
     * @return String response from command execution
     * @throws OmegaException If an error occurs during marking or saving.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws OmegaException {
        Task t = tasks.mark(index);
        storage.save(tasks);
        return ui.showMarked(t);
    }
}
