package omega.task;

import java.util.ArrayList;
import java.util.List;

import omega.OmegaException;

/**
 * Represents a list of tasks and provides methods to manipulate them.
 */
public record TaskList(List<Task> tasks) {
    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this(new ArrayList<>());
    }

    /**
     * Constructs a TaskList with the given list of tasks.
     *
     * @param tasks List of tasks to initialize the TaskList with.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a task to the task list.
     *
     * @param t Task to be added.
     */
    public void add(Task t) {
        tasks.add(t);
    }

    /**
     * Deletes a task at the specified index from the task list.
     *
     * @param index Index of the task to be deleted.
     * @return The deleted task.
     * @throws OmegaException If the index is out of bounds.
     */
    public Task delete(int index) throws OmegaException {
        ensureIndexExists(index);
        return tasks.remove(index);
    }

    /**
     * Marks a task at the specified index as completed.
     *
     * @param index Index of the task to be marked.
     * @return The marked task.
     * @throws OmegaException If the index is out of bounds.
     */
    public Task mark(int index) throws OmegaException {
        ensureIndexExists(index);
        Task t = tasks.get(index);
        t.markDone();
        return t;
    }

    /**
     * Unmarks a task at the specified index as not completed.
     *
     * @param index Index of the task to be unmarked.
     * @return The unmarked task.
     * @throws OmegaException If the index is out of bounds.
     */
    public Task unmark(int index) throws OmegaException {
        ensureIndexExists(index);
        Task t = tasks.get(index);
        t.unmarkDone();
        return t;
    }

    /**
     * Returns filtered task list by keyword.
     *
     * @return TaskList of filtered tasks.
     */
    public TaskList find(String keyword) {
        String search = keyword.toLowerCase();
        List<Task> filtered = tasks.stream()
                .filter(t -> t.description.toLowerCase().contains(search))
                .toList();
        return new TaskList(filtered);
    }

    /**
     * Checks if the task list is empty.
     *
     * @return true if the task list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return Number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the list of tasks as a List object.
     *
     * @return List of tasks.
     */
    public List<Task> toList() {
        return tasks;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i;
        for (i = 0; i < tasks.size() - 1; i++) {
            sb.append(String.format("%d.%s\n", i + 1, tasks.get(i)));
        }
        sb.append(String.format("%d.%s", i + 1, tasks.get(i)));
        return sb.toString();
    }

    private void ensureIndexExists(int index) throws OmegaException {
        if (index < 0 || index >= tasks.size()) {
            throw new OmegaException("omega.task.Task number out of bounds");
        }
    }
}
