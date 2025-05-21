package lv.wings.exception.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import lombok.Getter;


@Getter
public class NonLocalisedException extends RuntimeException {

    private final HttpStatusCode statusCode;

    public NonLocalisedException(String message) {
        super(message);
        statusCode = HttpStatus.BAD_REQUEST;
    }

    public NonLocalisedException(String message, HttpStatusCode statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
