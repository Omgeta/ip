package omega.task;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import omega.OmegaException;

/**
 * Represents a generic task with a description and completion status.
 */
public abstract class Task {
    public static final DateTimeFormatter OUT_PATTERN = DateTimeFormatter.ofPattern("MMM d yyyy");
    public static final DateTimeFormatter SAVE_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task with the specified description.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Parses a date string into a LocalDate object.
     *
     * @param dtString Date string to be parsed.
     * @return LocalDate object representing the parsed date.
     * @throws OmegaException If the date format is invalid.
     */
    protected static LocalDate parseDate(String dtString) throws OmegaException {
        try {
            return LocalDate.parse(dtString);
        } catch (DateTimeParseException e) {
            Parser parser = new Parser();
            List<DateGroup> groups = parser.parse(dtString);

            if (groups.isEmpty()) {
                throw new OmegaException("Failed to parse date: " + dtString);
            }

            Date date = groups.get(0).getDates().get(0);
            return date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
    }

    /**
     * Formats a LocalDate object into a string suitable for serialization.
     *
     * @param date LocalDate object to be formatted.
     * @return Formatted date string.
     */
    protected static String serializableDate(LocalDate date) {
        return date.format(Task.SAVE_PATTERN);
    }

    /**
     * Formats a LocalDate object into a string suitable for display.
     *
     * @param date LocalDate object to be formatted.
     * @return Formatted date string.
     */
    protected static String displayDate(LocalDate date) {
        return date.format(Task.OUT_PATTERN);
    }

    public void markDone() {
        this.isDone = true;
    }

    public void unmarkDone() {
        this.isDone = false;
    }

    /**
     * Returns the status icon representing whether the task is done.
     *
     * @return "X" if the task is done, " " otherwise.
     */
    protected String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Returns the type of the task.
     *
     * @return TaskType enum representing the type of the task.
     */
    protected abstract TaskType getType();

    /**
     * Returns extra fields for serialization specific to the task type.
     *
     * @return Map of extra fields for serialization.
     */
    protected abstract Map<String, String> getExtraSerializationFields();

    /**
     * Converts the task to a map suitable for serialization.
     *
     * @return Map representing the task's data for serialization.
     */
    public final Map<String, String> toSerializationMap() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("type", getType().code());
        map.put("done", isDone ? "1" : "0");
        map.put("desc", description);
        map.putAll(getExtraSerializationFields());

        assert map.containsKey("type") : "Serialization must include type";
        assert map.containsKey("done") : "Serialization must include done";
        assert map.containsKey("desc") : "Serialization must include desc";
        return map;
    }

    @Override
    public String toString() {
        return "[" + getType().code() + "]" + "[" + getStatusIcon() + "] " + this.description;
    }
}
