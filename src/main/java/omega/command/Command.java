package omega.command;

import omega.OmegaException;
import omega.storage.Storage;
import omega.task.TaskList;
import omega.ui.Ui;

/**
 * Represents a command that can be executed by the Omega application.
 */
public abstract class Command {
    /**
     * Executes command using the given task list, user interface, and storage.
     *
     * @param tasks   Task list to operate on.
     * @param ui      User interface for displaying messages.
     * @param storage Storage system for saving data.
     * @return String response from command execution
     * @throws OmegaException If an error occurs during command execution.
     */
    public abstract String execute(TaskList tasks, Ui ui, Storage storage) throws OmegaException;

    /**
     * Indicates whether this command is an exit command.
     *
     * @return true if this command is an exit command, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
