package lv.wings.exception.validation;

import lombok.Getter;

@Getter
public class InvalidParameterException extends RuntimeException {
    private final String queryNameCode;
    private final String queryParamValue;
    private final Boolean fromInterceptor;

    public InvalidParameterException(String queryNameCode, String queryParamValue, boolean fromInterceptor) {
        super("Invalid parameter: " + queryNameCode + " with value: " + queryParamValue);
        this.queryNameCode = queryNameCode;
        this.queryParamValue = queryParamValue;
        this.fromInterceptor = fromInterceptor;
    }
}
