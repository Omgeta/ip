import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class Deadline extends Task {
    protected LocalDate by;

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
