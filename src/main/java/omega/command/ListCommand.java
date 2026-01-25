package omega.command;

import omega.storage.Storage;
import omega.task.TaskList;
import omega.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks);
    }
}
