package lv.wings.config.interceptors;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lv.wings.exception.validation.InvalidParameterException;
import lv.wings.util.CustomValidator;

@Component
public class ParameterValidationInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                        throws InvalidParameterException {

                // path variables from the request attribute
                @SuppressWarnings("unchecked")
                Map<String, String> uriVariables = (Map<String, String>) request
                                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

                // validate an ID PathVariable
                String id = uriVariables != null ? uriVariables.get("id") : null;
                CustomValidator.shouldBeGreaterThanZero("id", id);

                // Validate pagination parameters
                CustomValidator.shouldBeGreaterThanZero("page", request.getParameter("page"));
                CustomValidator.shouldBeGreaterThanZero("size", request.getParameter("size"));

                // // Validate sorting parameters
                CustomValidator.validateAgainstAllowedValues("direction", request.getParameter("direction"), List.of("asc", "desc"));
                CustomValidator.validateAgainstAllowedValues("sort", request.getParameter("sort"),
                                CustomValidator.extractAllowedValuesFromAnnotation(handler));

                return true;
        }
}
