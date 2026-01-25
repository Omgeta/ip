package omega.command;

import omega.storage.Storage;
import omega.task.TaskList;
import omega.ui.Ui;

public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showBye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
