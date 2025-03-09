package lv.wings.config.interceptors;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lv.wings.exception.validation.InvalidQueryParameterException;

@Component
public class ParameterValidationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws InvalidQueryParameterException {

        // path variables from the request attribute
        @SuppressWarnings("unchecked")
        Map<String, String> uriVariables = (Map<String, String>) request
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        // validate an ID PathVariable
        String id = uriVariables != null ? uriVariables.get("id") : null;
        shouldBeGreaterThanZero("id", id);

        // Validate pagination parameters
        shouldBeGreaterThanZero("page", request.getParameter("page"));
        shouldBeGreaterThanZero("size", request.getParameter("size"));

        // // Validate sorting parameters
        validateAgainstAllowedValues("direction", request.getParameter("direction"), List.of("asc", "desc"));
        validateAgainstAllowedValues("sort", request.getParameter("sort"), List.of("title", "createdAt"));

        return true;
    }

    private void shouldBeGreaterThanZero(String paramName, String paramValue) {
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

    private void validateAgainstAllowedValues(String paramName, String paramValue, List<String> allowedValues) {
        if (paramValue != null && allowedValues.stream().noneMatch(value -> value.equalsIgnoreCase(paramValue))) {
            throw new InvalidQueryParameterException(paramName, paramValue, true);
        }
    }
}
