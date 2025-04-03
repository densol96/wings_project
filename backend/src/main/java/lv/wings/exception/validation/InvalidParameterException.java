package lv.wings.exception.validation;

import lombok.Getter;

@Getter
public class InvalidParameterException extends RuntimeException {
    private final String queryName;
    private final String queryValue;
    private final Boolean fromInterceptor;

    public InvalidParameterException(String queryName, String queryValue, boolean fromInterceptor) {
        super("Invalid parameter: " + queryName + " with value: " + queryValue);
        this.queryName = queryName;
        this.queryValue = queryValue;
        this.fromInterceptor = fromInterceptor;
    }
}
