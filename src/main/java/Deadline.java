import java.util.LinkedHashMap;
import java.util.Map;

public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    protected TaskType getType() {
        return TaskType.DEADLINE;
    }

    @Override
    protected Map<String, String> getExtraSerializationFields() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("by", by);
        return map;
    }


    @Override
    public String toString() {
        return super.toString() + " (by: " + by + ")";
    }
}
