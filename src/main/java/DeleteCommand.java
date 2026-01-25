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
