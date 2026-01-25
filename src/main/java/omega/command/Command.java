package omega.command;

import omega.OmegaException;
import omega.storage.Storage;
import omega.task.TaskList;
import omega.ui.Ui;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws OmegaException;

    public boolean isExit() {
        return false;
    }
}
