package lv.wings.exception.payment;

public class WebhookException extends RuntimeException {
    public WebhookException(String message) {
        super(message);
    }
}
