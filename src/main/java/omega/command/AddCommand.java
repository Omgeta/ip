package omega.command;

import omega.OmegaException;
import omega.storage.Storage;
import omega.task.Task;
import omega.task.TaskList;
import omega.ui.Ui;

public class AddCommand extends Command {
    private final Task taskToAdd;

    public AddCommand(Task taskToAdd) {
        this.taskToAdd = taskToAdd;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws OmegaException {
        tasks.add(taskToAdd);
        storage.save(tasks);
        ui.showAdded(taskToAdd, tasks.size());
    }
}
