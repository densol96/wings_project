package lv.wings.exception.other;

import lombok.Getter;

@Getter
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException() {
        super();
    }
}
