package omega.task;

import omega.OmegaException;

/**
 * Enum representing the different types of tasks.
 */
public enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E");

    private final String code;

    TaskType(String code) {
        this.code = code;
    }

    /**
     * Returns the TaskType corresponding to the given code.
     *
     * @param code Code representing the task type.
     * @return Corresponding TaskType.
     * @throws OmegaException If the code does not match any TaskType.
     */
    public static TaskType fromCode(String code) throws OmegaException {
        for (TaskType t : values()) {
            if (t.code.equals(code)) {
                return t;
            }
        }
        throw new OmegaException("Unknown task type: " + code);
    }

    public String code() {
        return code;
    }
}
