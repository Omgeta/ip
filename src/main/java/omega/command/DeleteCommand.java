package omega.command;

import omega.OmegaException;
import omega.storage.Storage;
import omega.task.Task;
import omega.task.TaskList;
import omega.ui.Ui;

public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws OmegaException {
        Task t = tasks.delete(index);
        storage.save(tasks);
        ui.showDeleted(t, tasks.size());
    }
}
