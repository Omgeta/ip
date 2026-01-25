package omega.task;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import omega.OmegaException;

/**
 * Represents a deadline task with a description and a due date.
 */
public class Deadline extends Task {
    protected LocalDate by;

    /**
     * Constructs a Deadline task with the specified description and due date.
     *
     * @param description Description of the deadline task.
     * @param by          Due date of the deadline task in string format.
     * @throws OmegaException If the date format is invalid.
     */
    public Deadline(String description, String by) throws OmegaException {
        super(description);
        this.by = Task.parseDate(by);
    }

    @Override
    protected TaskType getType() {
        return TaskType.DEADLINE;
    }

    @Override
    protected Map<String, String> getExtraSerializationFields() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("by", Task.serializableDate(by));
        return map;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + Task.displayDate(by) + ")";
    }
}
