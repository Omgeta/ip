package omega.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import omega.storage.Storage;
import omega.task.Deadline;
import omega.task.TaskList;
import omega.task.Todo;
import omega.ui.Ui;

/**
 * Test class for Command implementations.
 */
public class CommandTest {
    // Temporary directory for testing storage
    @TempDir
    Path tempDir;

    private Storage storage(Path file) {
        return new Storage(file);
    }

    private Ui ui() {
        return new Ui();
    }

    /**
     * Tests that AddCommand adds a task to the tasklist and saves to storage.
     *
     * @throws Exception If command fails unexpectedly
     */
    @Test
    public void execute_addCommand_addsTaskAndSaves() throws Exception {
        Path file = tempDir.resolve("tasks.txt");
        Storage storage = storage(file);
        Ui ui = ui();
        TaskList tasks = new TaskList();

        AddCommand cmd = new AddCommand(new Todo("eat food"));
        String out = cmd.execute(tasks, ui, storage);

        assertEquals(1, tasks.size());
        assertTrue(out.contains("eat food"));

        assertTrue(Files.exists(file));
        String saved = Files.readString(file);
        assertTrue(saved.contains("desc=eat food"), "Saved file should contain the task description");
    }

    /**
     * Tests that DeleteCommand deletes a task from the tasklist and saves to storage.
     *
     * @throws Exception If command fails unexpectedly
     */
    @Test
    public void execute_deleteCommand_deletesTaskAndSaves() throws Exception {
        Path file = tempDir.resolve("tasks.txt");
        Storage storage = storage(file);
        Ui ui = ui();
        TaskList tasks = new TaskList();
        tasks.add(new Todo("eat food"));
        tasks.add(new Todo("cook dinner"));
        storage.save(tasks);

        DeleteCommand cmd = new DeleteCommand(0); // 0-based
        String out = cmd.execute(tasks, ui, storage);

        assertEquals(1, tasks.size());
        assertTrue(out.contains("eat food"));

        String saved = Files.readString(file);
        assertTrue(saved.contains("desc=cook dinner"), "Remaining task should still be saved");
        assertFalse(saved.contains("desc=eat food"), "Deleted task should not remain in saved file");
    }

    /**
     * Tests that MarkCommand marks a task done in the tasklist and saves to storage.
     *
     * @throws Exception If command fails unexpectedly
     */
    @Test
    public void execute_markCommand_marksAndSaves() throws Exception {
        Path file = tempDir.resolve("tasks.txt");
        Storage storage = storage(file);
        Ui ui = ui();
        TaskList tasks = new TaskList();
        tasks.add(new Todo("eat food"));
        storage.save(tasks);

        MarkCommand cmd = new MarkCommand(0);
        String out = cmd.execute(tasks, ui, storage);

        assertTrue(tasks.toList().get(0).toString().contains("[X]"));

        String saved = Files.readString(file);
        assertTrue(saved.contains("done=1"), "Saved file should reflect done=1 after marking");
    }

    /**
     * Tests that UnmarkCommand unmarks a task as done in the tasklist and saves to storage.
     *
     * @throws Exception If command fails unexpectedly
     */
    @Test
    public void execute_unmarkCommand_unmarksAndSaves() throws Exception {
        Path file = tempDir.resolve("tasks.txt");
        Storage storage = storage(file);
        Ui ui = ui();
        TaskList tasks = new TaskList();
        tasks.add(new Todo("eat food"));
        tasks.mark(0); // mark first
        storage.save(tasks);

        UnmarkCommand cmd = new UnmarkCommand(0);
        String out = cmd.execute(tasks, ui, storage);

        assertTrue(tasks.toList().get(0).toString().contains("[ ]"));

        String saved = Files.readString(file);
        assertTrue(saved.contains("done=0"), "Saved file should reflect done=0 after unmarking");
    }


    /**
     * Tests that ListCommand shows all added tasks.
     *
     * @throws Exception If command fails unexpectedly
     */
    @Test
    public void execute_listCommand_showsTasks() throws Exception {
        Ui ui = ui();
        TaskList tasks = new TaskList();
        tasks.add(new Todo("eat food"));
        tasks.add(new Deadline("cook dinner", "2026-02-30"));

        ListCommand cmd = new ListCommand();
        String out = cmd.execute(tasks, ui, null);

        assertTrue(out.contains("1."));
        assertTrue(out.contains("eat food"));
        assertTrue(out.contains("2."));
        assertTrue(out.contains("cook dinner"));
    }

    /**
     * Tests that FindCommand shows all matching tasks.
     */
    @Test
    public void execute_findCommand_showsMatchingList() {
        Ui ui = ui();
        TaskList tasks = new TaskList();
        tasks.add(new Todo("eat food"));
        tasks.add(new Todo("buy bread"));

        FindCommand cmd = new FindCommand("food");
        String out = cmd.execute(tasks, ui, null);

        assertTrue(out.contains("eat food"));
        assertFalse(out.contains("buy bread"));
    }
}
