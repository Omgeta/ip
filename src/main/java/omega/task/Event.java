package omega.task;

import omega.OmegaException;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

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
