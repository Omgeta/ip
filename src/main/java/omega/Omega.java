package omega;

import omega.command.Command;
import omega.parser.Parser;
import omega.storage.Storage;
import omega.task.TaskList;
import omega.ui.Ui;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Omega {
    private static final Path FILE_PATH = Paths.get("data", "tasks.txt");
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    public Omega(Path filePath) {
        TaskList loaded;
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            loaded = new TaskList(storage.load());
        } catch (OmegaException e) {
            ui.showError(e.getMessage());
            loaded = new TaskList();
        }
        this.tasks = loaded;
    }

    public static void main(String[] args) {
        new Omega(FILE_PATH).run();
    }

    public void run() {
        ui.showWelcome();

        boolean isExit = false;
        while (!isExit) {
            try {
                String input = ui.readCommand();
                ui.showLine();
                Command cmd = Parser.parse(input);
                cmd.execute(tasks, ui, storage);
                isExit = cmd.isExit();
            } catch (OmegaException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

}


