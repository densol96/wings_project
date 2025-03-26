package lv.wings.util;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.method.HandlerMethod;
import lv.wings.annotation.AllowedSortFields;
import lv.wings.exception.validation.InvalidQueryParameterException;

public class CustomValidator {

    public static List<String> extractAllowedValuesFromAnnotation(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AllowedSortFields allowedSortFields = handlerMethod.getMethodAnnotation(AllowedSortFields.class);
            if (allowedSortFields != null)
                return Arrays.asList(allowedSortFields.value());
        }
        return List.of();
    }

    public static void shouldBeGreaterThanZero(String paramName, String paramValue) {
        if (paramValue != null) {
            var exception = new InvalidQueryParameterException(paramName, paramValue, true);
            try {
                int value = Integer.parseInt(paramValue);
                if (value <= 0)
                    throw exception;
            } catch (NumberFormatException e) {
                throw exception;
            }
        }
    }

    public static void validateAgainstAllowedValues(String paramName, String paramValue, List<String> allowedValues) {
        if (paramValue != null && allowedValues.stream().noneMatch(value -> value.equalsIgnoreCase(paramValue))) {
            throw new InvalidQueryParameterException(paramName, paramValue, true);
        }
    }
}
