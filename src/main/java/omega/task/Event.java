package omega.task;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import omega.OmegaException;

/**
 * Represents an event task with a description, start date, and end date.
 */
public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    /**
     * Constructs an Event task with the specified description, start and end date.
     *
     * @param description Description of the event task.
     * @param from        Start date of the event task in string format.
     * @param to          End date of the event task in string format.
     * @throws OmegaException If the date format is invalid.
     */
    public Event(String description, String from, String to) throws OmegaException {
        super(description);
        this.from = Task.parseDate(from);
        this.to = Task.parseDate(to);
    }

    @Override
    protected TaskType getType() {
        return TaskType.EVENT;
    }

    @Override
    protected Map<String, String> getExtraSerializationFields() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("from", Task.serializableDate(from));
        map.put("to", Task.serializableDate(to));
        return map;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + Task.displayDate(from) + " to: " + Task.displayDate(to) + ")";
    }
}
