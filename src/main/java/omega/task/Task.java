package omega.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Map;

import omega.OmegaException;

public abstract class Task {
    public static final DateTimeFormatter OUT_PATTERN = DateTimeFormatter.ofPattern("MMM d yyyy");
    public static final DateTimeFormatter SAVE_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    protected static LocalDate parseDate(String dtString) throws OmegaException {
        try {
            return LocalDate.parse(dtString);
        } catch (DateTimeParseException e) {
            throw new OmegaException("Failed to parse date: " + e.getMessage());
        }
    }

    protected static String serializableDate(LocalDate date) {
        return date.format(Task.SAVE_PATTERN);
    }

    protected static String displayDate(LocalDate date) {
        return date.format(Task.OUT_PATTERN);
    }

    public void markDone() {
        this.isDone = true;
    }

    public void unmarkDone() {
        this.isDone = false;
    }

    protected String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    protected abstract TaskType getType();

    protected abstract Map<String, String> getExtraSerializationFields();

    public final Map<String, String> toSerializationMap() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("type", getType().code());
        map.put("done", isDone ? "1" : "0");
        map.put("desc", description);
        map.putAll(getExtraSerializationFields());
        return map;
    }

    @Override
    public String toString() {
        return "[" + getType().code() + "]" + "[" + getStatusIcon() + "] " + this.description;
    }
}
