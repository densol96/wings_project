package lv.wings.exception;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lv.wings.dto.response.BasicErrorDto;
import lv.wings.dto.response.payment.CheckoutErrorDto;
import lv.wings.enums.CheckoutStep;
import lv.wings.exception.coupon.InvalidCouponException;
import lv.wings.exception.entity.EntityNotFoundException;
import lv.wings.exception.entity.MissingTranslationException;
import lv.wings.exception.other.AlreadySubscribedException;
import lv.wings.exception.other.TokenNotFoundException;
import lv.wings.exception.payment.CheckoutException;
import lv.wings.exception.payment.WebhookException;
import lv.wings.exception.validation.InvalidFieldsException;
import lv.wings.exception.validation.InvalidParameterException;
import lv.wings.model.security.User;
import lv.wings.service.LocaleService;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final LocaleService localeService;
    private final AuditorAware<User> auditorService;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BasicErrorDto> handleConversionError(MethodArgumentTypeMismatchException e) {
        log.error("*** Conversion error as MethodArgumentTypeMismatchException: {}.", e.getMessage());

        return handleInvalidQueryParameterException(
                new InvalidParameterException(e.getName(), e.getValue().toString(), false));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BasicErrorDto> handleBadCredentialsException(BadCredentialsException e) {
        log.error("*** BadCredentialsException: {}.", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new BasicErrorDto(localeService.getMessage("error.bad_credentials")));
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<BasicErrorDto> handleDisabledException(DisabledException e) {
        log.error("*** DisabledException: {}.", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new BasicErrorDto(localeService.getMessage("error.disabled")));
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<BasicErrorDto> handleAuthRequired(AuthenticationCredentialsNotFoundException e) {
        log.error("*** AuthenticationCredentialsNotFoundException: {}.", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new BasicErrorDto(localeService.getMessage("error.unauthorized")));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BasicErrorDto> handleAccessDenied() {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new BasicErrorDto(localeService.getMessage("error.forbidden")));
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<BasicErrorDto> handleInvalidQueryParameterException(InvalidParameterException e) {
        if (e.getFromInterceptor()) {
            log.error("*** Interceptor detected InvalidQueryParameterException: {}", e.getMessage());
        } else {
            log.error("*** Control flow from MethodArgumentTypeMismatchException: {}", e.getMessage());
        }

        String localisedParameterName = localeService.getMessage(e.getQueryNameCode());
        String message = localeService.getMessage("error.invalid-query-param", new Object[] {e.getQueryParamValue(), localisedParameterName});
        return ResponseEntity.badRequest().body(new BasicErrorDto(message));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<BasicErrorDto> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("*** EntityNotFoundException: {}", e.getMessage());
        String localizedEntityName = localeService.getMessage(e.getEntityNameKey());
        String message = localeService.getMessage("error.entity-not-found", new Object[] {localizedEntityName, e.getEntityId()});
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new BasicErrorDto(message));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("*** MethodArgumentNotValidException: {}", e.getMessage());

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    @ExceptionHandler(InvalidFieldsException.class)
    public ResponseEntity<Map<String, String>> handleInvalidFieldsException(InvalidFieldsException e) {
        log.error("*** InvalidFieldsException: {}", e.getMessage());

        Map<String, String> localisedErrors = new HashMap<>();

        e.getFieldErrors().forEach((key, value) -> {
            localisedErrors.put(key, localeService.getMessage(value));
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(localisedErrors);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<BasicErrorDto> handleMissingParams(MissingServletRequestParameterException e) {
        String name = e.getParameterName();
        log.error("*** MissingServletRequestParameterException: Parameter name - {}", name);

        String message = localeService.getMessage("error.missing-parameter", new Object[] {name});
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BasicErrorDto(message));
    }

    @ExceptionHandler(AlreadySubscribedException.class)
    public ResponseEntity<BasicErrorDto> handleAlreadySubscribedException(AlreadySubscribedException e) {
        log.error("*** AlreadySubscribedException: {}", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(BasicErrorDto.builder().message(localeService.getMessage("newslettersubscriber.already-subscribed")).build());
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<BasicErrorDto> handleTokenNotFoundException(TokenNotFoundException e) {
        log.error("*** TokenNotFoundException: {}", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(BasicErrorDto.builder().message(localeService.getMessage("token.not-found")).build());
    }



    /**
     * Hibernate actually checks Bean Validation annotations during .save but wraps ConstraintViolationException inside TransactionSystemException class.
     * 
     * In my app, I do @Valid on DTOs anyway, but I added this handler as a reference for future.
     */

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<Map<String, String>> handleFailedTransaction(TransactionSystemException e) {
        Throwable cause = e.getCause();
        while (cause != null) {
            if (cause instanceof ConstraintViolationException cve) {
                Map<String, String> errors = new HashMap<>();
                for (ConstraintViolation<?> violation : cve.getConstraintViolations()) {
                    String field = violation.getPropertyPath().toString();
                    String message = violation.getMessage();
                    errors.put(field, message);
                }

                return ResponseEntity.badRequest().body(errors);
            }
            cause = cause.getCause();
        }

        return ResponseEntity.internalServerError().body(Map.of(
                "message", "Unexpected transaction error"));
    }

    @ExceptionHandler(MissingTranslationException.class)
    public ResponseEntity<BasicErrorDto> handleMissingTranslationException(MissingTranslationException e) {
        log.error("*** MissingTranslationException: {}", e.getMessage());

        Optional<User> authenticatedUser = auditorService.getCurrentAuditor();

        // Add a check if the authenticated is not an admin / employee later (when implement it)
        if (authenticatedUser.isEmpty()) {
            // If just a guest / or user, display a general message
            return handleProceduralException(e);
        }
        // Else, inform the admin / employee that some translation is missing
        String localizedEntityName = localeService.getMessage(e.getEntityNameKey());
        String message = localeService.getMessage(
                "error.translation-not-found",
                new Object[] {localizedEntityName, e.getEntityId()});

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new BasicErrorDto(message));
    }

    @ExceptionHandler(InvalidCouponException.class)
    public ResponseEntity<BasicErrorDto> handleInvalidCoupon(InvalidCouponException e) {
        /**
         * withPrefixRemoved
         * Various scenarios available for this type of Exception => aproch to handle slightly differs.
         * 
         * Pass in an appropriate code and minimum amount required to spend.
         */
        String localizedMessage = localeService.getMessage(
                e.getMessageCode(),
                new Object[] {e.getMinAmount()});
        log.error("*** InvalidCouponException: {}", localizedMessage);
        return ResponseEntity.badRequest().body(new BasicErrorDto(localizedMessage));
    }

    @ExceptionHandler(CheckoutException.class)
    public ResponseEntity<CheckoutErrorDto> handleCheckoutException(CheckoutException e) {
        String localizedMessage = localeService.getMessage(
                "payment-intent.invalid." + e.getErrorCode(),
                new Object[] {e.getMaxAmount()});
        log.error("*** FailedIntentException: {}", localizedMessage);
        CheckoutErrorDto errorResponse = CheckoutErrorDto.builder()
                .message(localizedMessage)
                .step(e.getStep())
                .errorCode(e.getErrorCode())
                .invalidIds(e.getInvalidIds())
                .maxAmount(e.getMaxAmount())
                .fieldErrors(e.getFieldErrors())
                .build();
        if (e.getStep() == CheckoutStep.SERVER)
            return ResponseEntity.internalServerError().body(errorResponse);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(WebhookException.class)
    public ResponseEntity<String> handleWebhookException(Exception e) {
        log.error("*** WebhookException: {}", e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BasicErrorDto> handleUnexpectedException(Exception e) {
        log.error("*** Unexpected exception of type {}: {}", e.getClass().getSimpleName(), e.getMessage());
        // e.printStackTrace();
        return handleProceduralException(e);
    }

    private ResponseEntity<BasicErrorDto> handleProceduralException(Exception e) {
        // String message = activeProfile.equals("dev") ? e.getMessage()
        // : messageSource.getMessage("error.internal", null, locale);

        String message = localeService.getMessage("error.internal");
        return ResponseEntity
                .internalServerError()
                .body(new BasicErrorDto(message));
    }
}

// EXCEPTIONS I WANT TO BE HANDLED AS PROCEDURAL (log details but return a
// general message to a client):

// "Failed to convert value of type 'java.lang.String' to required type
// 'lv.wings.enums.SortBy'; Failed to convert from type [java.lang.String] to
// type [@org.springframework.web.bind.annotation.RequestParam
// lv.wings.enums.SortBy] for value [createdAfdsfsdf]"
