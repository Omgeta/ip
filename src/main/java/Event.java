import java.util.LinkedHashMap;
import java.util.Map;

public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    protected TaskType getType() {
        return TaskType.EVENT;
    }

    @Override
    protected Map<String, String> getExtraSerializationFields() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("from", from);
        map.put("to", to);
        return map;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
