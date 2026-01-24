import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void unmarkDone() {
        this.isDone = false;
    }

    protected abstract TaskType getType();

    protected String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

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