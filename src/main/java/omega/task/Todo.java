package omega.task;

import java.util.Map;

/**
 * Represents a todo task with a description.
 */
public class Todo extends Task {
    /**
     * Constructs a Todo task with the specified description.
     *
     * @param description Description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    protected TaskType getType() {
        return TaskType.TODO;
    }

    @Override
    protected Map<String, String> getExtraSerializationFields() {
        return Map.of();
    }
}
