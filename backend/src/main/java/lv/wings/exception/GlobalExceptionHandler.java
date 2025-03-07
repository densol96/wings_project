package lv.wings.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.dto.response.BasicErrorDto;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BasicErrorDto> handleUnexpectedException(Exception e, Locale locale) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        log.error("Unexpected exception of type {}: {}", e.getClass().getSimpleName(), e.getMessage());
        System.out.println("CURRENT LOCALE ==> " + currentLocale);
        System.out.println("JUST LOCALE ==> " + locale);
        return ResponseEntity
                .internalServerError()
                .body(BasicErrorDto.builder().message(messageSource.getMessage("error.internal", null, currentLocale))
                        .build());
    }
}
