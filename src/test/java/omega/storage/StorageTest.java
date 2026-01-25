package omega.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import omega.OmegaException;
import omega.task.Deadline;
import omega.task.Event;
import omega.task.Task;
import omega.task.TaskList;
import omega.task.Todo;

public class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    public void load_fileDoesNotExist_createsFileAndLoadsEmpty() throws Exception {
        Path dataDir = tempDir.resolve("data");
        Path file = dataDir.resolve("tasks.txt");

        Storage storage = new Storage(file);

        List<Task> tasks = storage.load();

        assertTrue(Files.exists(dataDir));
        assertTrue(Files.exists(file));
        assertEquals(0, tasks.size());
    }

    @Test
    public void saveThenLoad_addValidTasks_preservesTaskState() throws Exception {
        Path file = tempDir.resolve("tasks.txt");
        Storage storage = new Storage(file);

        TaskList original = new TaskList();
        original.add(new Todo("read book"));
        original.add(new Deadline("return book", "2019-10-15"));
        original.add(new Event("project meeting", "2019-10-15", "2019-10-16"));

        // Mark one as done before saving
        original.mark(1);

        storage.save(original);

        List<Task> loaded = storage.load();

        assertEquals(original.size(), loaded.size(), "Loaded list size should match saved list size");

        for (int i = 0; i < original.size(); i++) {
            assertEquals(original.toList().get(i).toString(), loaded.get(i).toString());
        }
    }

    @Test
    public void load_unknownType_throwsException() throws Exception {
        Path file = tempDir.resolve("tasks.txt");
        Storage storage = new Storage(file);

        // type=Z is not valid
        Files.createDirectories(file.getParent());
        Files.writeString(file, "type=Z\ndone=0\ndesc=corrupted\n\n");

        assertThrows(OmegaException.class, storage::load);
    }
}
