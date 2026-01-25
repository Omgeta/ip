package omega.command;

import omega.storage.Storage;
import omega.task.TaskList;
import omega.ui.Ui;

public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        TaskList matches = tasks.find(keyword);
        ui.showFindList(keyword, matches);
    }
}
