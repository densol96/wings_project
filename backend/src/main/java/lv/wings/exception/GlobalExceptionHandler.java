package lv.wings.exception;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.dto.response.BasicErrorDto;
import lv.wings.exception.validation.InvalidQueryParameterException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BasicErrorDto> handleConversionError(MethodArgumentTypeMismatchException e, Locale locale) {
        log.error("*** Conversion error: {}.", e.getMessage());
        return handleInvalidQueryParameterException(
                new InvalidQueryParameterException(e.getName(), e.getValue().toString(), false),
                locale);
    }

    @ExceptionHandler(InvalidQueryParameterException.class)
    public ResponseEntity<BasicErrorDto> handleInvalidQueryParameterException(InvalidQueryParameterException e,
            Locale locale) {
        if (e.getFromInterceptor()) {
            log.error("*** Interceptor detected a query-param data-format error: {}", e.getMessage());
        } else {
            log.error("*** Control flow from MethodArgumentTypeMismatchException: {}", e.getMessage());
        }

        String message = messageSource.getMessage("error.invalid-query-param",
                new Object[] { e.getQueryName(), e.getQueryValue() }, locale);
        return ResponseEntity.badRequest().body(BasicErrorDto.builder().message(message).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BasicErrorDto> handleUnexpectedException(Exception e, Locale locale) {
        log.error("*** Unexpected exception of type {}: {}", e.getClass().getSimpleName(), e.getMessage());
        return handleProceduralException(e, locale);
    }

    private ResponseEntity<BasicErrorDto> handleProceduralException(Exception e, Locale locale) {
        // String message = activeProfile.equals("dev") ? e.getMessage()
        // : messageSource.getMessage("error.internal", null, locale);

        String message = messageSource.getMessage("error.internal", null, locale);

        return ResponseEntity
                .internalServerError()
                .body(BasicErrorDto.builder().message(message).build());
    }
}

// EXCEPTIONS I WANT TO BE HANDLED AS PROCEDURAL (log details but return a
// general message to a client):

// "Failed to convert value of type 'java.lang.String' to required type
// 'lv.wings.enums.SortBy'; Failed to convert from type [java.lang.String] to
// type [@org.springframework.web.bind.annotation.RequestParam
// lv.wings.enums.SortBy] for value [createdAfdsfsdf]"
