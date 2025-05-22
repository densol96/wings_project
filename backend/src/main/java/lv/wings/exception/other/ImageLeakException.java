package lv.wings.exception.other;

public class ImageLeakException extends RuntimeException {
    public ImageLeakException(String message) {
        super(message);
    }

    public ImageLeakException() {
        super();
    }
}
