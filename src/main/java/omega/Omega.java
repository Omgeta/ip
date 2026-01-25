package omega;

import java.nio.file.Path;
import java.nio.file.Paths;

import omega.command.Command;
import omega.parser.Parser;
import omega.storage.Storage;
import omega.task.TaskList;
import omega.ui.Ui;

/**
 * Main class for the Omega application.
 */
public class Omega {
    private static final Path FILE_PATH = Paths.get("data", "tasks.txt");
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Constructs the Omega application with the specified file path for storage.
     *
     * @param filePath Path to the file where tasks are stored.
     */
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

    /**
     * Main method to start the Omega application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Omega(FILE_PATH).run();
    }

    /**
     * Runs the main loop of the Omega application.
     */
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
