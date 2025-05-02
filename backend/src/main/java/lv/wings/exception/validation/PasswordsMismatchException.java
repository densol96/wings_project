package lv.wings.exception.validation;

public class PasswordsMismatchException extends RuntimeException {
    public PasswordsMismatchException(String message) {
        super(message);
    }
}
