import java.util.Map;

public class Todo extends Task {
    private static final String TYPE_PREFIX = "T";

    public Todo(String description) {
        super(description);
    }

    @Override
    protected String getTypePrefix() {
        return "T";
    }

    @Override
    protected Map<String, String> getExtraSerializationFields() {
        return Map.of();
    }
}
