package lv.wings.exception.validation;

import lombok.Getter;

@Getter
public class InvalidQueryParameterException extends RuntimeException {
    private String queryName;
    private String queryValue;
    private Boolean fromInterceptor;

    public InvalidQueryParameterException(String queryName, String queryValue, boolean fromInterceptor) {
        super("Invalid query parameter: " + queryName + " with value: " + queryValue);
        this.queryName = queryName;
        this.queryValue = queryValue;
        this.fromInterceptor = fromInterceptor;
    }
}