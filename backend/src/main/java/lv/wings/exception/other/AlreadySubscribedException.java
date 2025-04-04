package lv.wings.exception.other;

public class AlreadySubscribedException extends RuntimeException {
    public AlreadySubscribedException(String message) {
        super(message);
    }
}
