package lv.wings.config.interceptors;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lv.wings.exception.validation.InvalidQueryParameterException;

public class ParameterValidationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws InvalidQueryParameterException {

        // throws
        shouldBeGreaterThanZero("id", request.getParameter("id"));
        shouldBeGreaterThanZero("page", request.getParameter("page"));
        shouldBeGreaterThanZero("size", request.getParameter("size"));
        validateAgainstAllowedValues("sortDirection", request.getParameter("sortDirection"),
                new ArrayList<>(List.of("asc", "desc")));
        return true;
    }

    private void shouldBeGreaterThanZero(String paramName, String paramValue) {
        if (paramValue != null) {
            var e = new InvalidQueryParameterException(paramName, paramValue, true);
            try {
                int resultsPerPage = Integer.parseInt(paramValue);
                if (resultsPerPage <= 0) {
                    throw e;
                }
            } catch (NumberFormatException exc) {
                throw e;
            }
        }
    }

    private void validateAgainstAllowedValues(String paramName, String paramValue, List<String> allowedValues) {
        if (paramValue != null && !allowedValues.stream()
                .anyMatch(value -> value.equalsIgnoreCase(paramValue))) {
            throw new InvalidQueryParameterException(paramName, paramValue, true);
        }
    }
}