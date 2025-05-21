package lv.wings.exception.validation;

import java.util.Map;

public class NestedValidationException extends RuntimeException {
    private final Map<String, Object> errors;

    public NestedValidationException(Map<String, Object> errors) {
        super("Validation failed");
        this.errors = errors;
    }

    public Map<String, Object> getErrors() {
        return errors;
    }
}
