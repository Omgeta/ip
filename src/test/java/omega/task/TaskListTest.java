package omega.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import omega.OmegaException;

/**
 * Test class for TaskList.
 */
public class TaskListTest {
    /**
     * Tests that adding tasks updates the size and isEmpty status correctly.
     */
    @Test
    public void addAndSize_validTask_addsTasksAndUpdatesSize() {
        TaskList list = new TaskList();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());

        list.add(new Todo("read book"));
        list.add(new Todo("return book"));

        assertEquals(2, list.size());
        assertFalse(list.isEmpty());
    }

    /**
     * Tests that marking and unmarking tasks toggles their done state.
     *
     * @throws Exception If an error occurs during the test.
     */
    @Test
    public void markAndUnmark_validTask_togglesDoneState() throws Exception {
        TaskList list = new TaskList();
        list.add(new Todo("read book"));

        Task marked = list.mark(0);
        assertTrue(marked.toString().contains("[X]"));

        Task unmarked = list.unmark(0);
        assertTrue(unmarked.toString().contains("[ ]"));
    }

    /**
     * Tests that marking a task with an out-of-bounds index throws OmegaException.
     */
    @Test
    public void mark_outOfBounds_throwsException() {
        TaskList list = new TaskList();
        list.add(new Todo("read book"));

        assertThrows(OmegaException.class, () -> list.mark(-1));
        assertThrows(OmegaException.class, () -> list.mark(1));
    }

    /**
     * Tests that unmarking a task with an out-of-bounds index throws
     * OmegaException.
     */
    @Test
    public void delete_validTask_removesAndShiftsRemaining() throws Exception {
        TaskList list = new TaskList();
        list.add(new Todo("read book"));
        list.add(new Todo("return book"));
        list.add(new Todo("buy bread"));

        Task removed = list.delete(1); // remove "return book"
        assertTrue(removed.toString().contains("return book"));
        assertEquals(2, list.size());

        // After deletion, former index 2 ("buy bread") should now be at index 1
        assertTrue(list.toList().get(1).toString().contains("buy bread"));
    }

    /**
     * Tests that toString method enumerates tasks correctly.
     */
    @Test
    public void toString_validTasks_enumeratesTasks() {
        TaskList list = new TaskList();
        list.add(new Todo("read book"));
        list.add(new Todo("return book"));

        String[] lines = list.toString().split("\n");
        assertEquals(2, lines.length);

        assertTrue(lines[0].startsWith("1."), "First line should start with 1.");
        assertTrue(lines[1].startsWith("2."), "Second line should start with 2.");

        assertTrue(lines[0].contains("read book"));
        assertTrue(lines[1].contains("return book"));
    }
}
