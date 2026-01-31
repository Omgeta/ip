package omega.command;

import omega.storage.Storage;
import omega.task.TaskList;
import omega.ui.Ui;

/**
 * Represents a command to exit the application.
 */
public class ExitCommand extends Command {
    /**
     * Displays goodbye message to the user.
     *
     * @param tasks   Task list (not used in this command).
     * @param ui      User interface to show goodbye message.
     * @param storage Storage system (not used in this command).
     * @return String response from command execution
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return ui.showBye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
