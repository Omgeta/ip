public enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E");

    private final String code;

    TaskType(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    public static TaskType fromCode(String code) throws OmegaException {
        for (TaskType t : values())
            if (t.code.equals(code)) return t;
        throw new OmegaException("Unknown task type: " + code);
    }
}
