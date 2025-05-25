package lv.wings.exception.validation;

import java.util.Set;
import jakarta.validation.ConstraintViolation;
import lombok.Getter;

@Getter
public class ConstraintValidationException extends RuntimeException {
    private Set<ConstraintViolation<?>> violations;

    public ConstraintValidationException(String message, Set<ConstraintViolation<?>> violations) {
        super(message);
        this.violations = violations;
    }

    public ConstraintValidationException(Set<ConstraintViolation<?>> violations) {
        super();
        this.violations = violations;
    }
}
