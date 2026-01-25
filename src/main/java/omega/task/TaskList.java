package omega.task;

import java.util.ArrayList;
import java.util.List;

import omega.OmegaException;

public record TaskList(List<Task> tasks) {
    public TaskList() {
        this(new ArrayList<>());
    }

    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    public void add(Task t) {
        tasks.add(t);
    }

    public Task delete(int index) throws OmegaException {
        ensureIndexExists(index);
        return tasks.remove(index);
    }

    public Task mark(int index) throws OmegaException {
        ensureIndexExists(index);
        Task t = tasks.get(index);
        t.markDone();
        return t;
    }

    public Task unmark(int index) throws OmegaException {
        ensureIndexExists(index);
        Task t = tasks.get(index);
        t.unmarkDone();
        return t;
    }

    public TaskList find(String keyword) {
        keyword = keyword.toLowerCase();
        TaskList matches = new TaskList();

        for (Task t : tasks) {
            if (t.description.toLowerCase().contains(keyword)) {
                matches.add(t);
            }
        }
        return matches;
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public int size() {
        return tasks.size();
    }

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
