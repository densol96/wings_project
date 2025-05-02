package lv.wings.exception.validation;

import java.util.Map;
import lombok.Getter;

@Getter
public class InvalidFieldsException extends RuntimeException {

    private Map<String, String> fieldErrors;

    public InvalidFieldsException(Map<String, String> fieldErrors) {
        super();
        this.fieldErrors = fieldErrors;
    }
}
