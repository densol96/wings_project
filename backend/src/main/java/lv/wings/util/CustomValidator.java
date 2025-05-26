package lv.wings.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.method.HandlerMethod;
import io.micrometer.common.lang.Nullable;
import lv.wings.annotation.AllowedSortFields;
import lv.wings.dto.interfaces.HasTranslationMethod;
import lv.wings.enums.LocaleCode;
import lv.wings.exception.validation.InvalidIdException;
import lv.wings.exception.validation.InvalidParameterException;
import lv.wings.model.base.TranslatableEntity;
import lv.wings.model.interfaces.Localable;
import lv.wings.model.interfaces.LocalableWithTitle;
import lv.wings.repo.base.TitleWithLocaleAndSoftDeleteRepository;
import lv.wings.util.mapping.EntityExistenceProvider;

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
            var exception = new InvalidParameterException(paramName, paramValue, true);
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
        if (paramValue != null && allowedValues != null && allowedValues.size() > 0
                && allowedValues.stream().noneMatch(value -> value.equalsIgnoreCase(paramValue))) {
            throw new InvalidParameterException(paramName, paramValue, true);
        }
    }

    public static void isValidId(String idName, Integer idValue) {
        if (idValue == null || idValue < 1)
            throw new InvalidIdException(idName, idValue);
    }

    public static void isValidId(Integer id) {
        isValidId("id", id);
    }

    public static boolean userIsAllowedAccess(UserDetails user) {
        return user.isEnabled() && user.isAccountNonExpired() && user.isAccountNonLocked() && user.isCredentialsNonExpired();
    }

    // public static <T> Map<String, Object> validateTitleUniqueness(
    // HasTranslationMethod dto,
    // TitleWithLocaleAndSoftDeleteRepository<T> repository,
    // Map<String, Object> errors,
    // @Nullable List<LocalableWithTitle> existingTranslations) {
    // dto.getTranslations().forEach(tr -> {
    // String newTitle = tr.getTitle();
    // LocaleCode forLocale = tr.getLocale();
    // boolean isCreating = existingTranslations == null;
    // boolean titleAlreadyExists = repository.existsByTitleAndLocaleAndDeletedFalse(newTitle, forLocale);
    // boolean titleRemainsTheSame = !isCreating && existingTranslations
    // .stream()
    // .filter(existingTr -> existingTr.getLocale() == forLocale && newTitle.equals(existingTr.getTitle()))
    // .findFirst()
    // .isPresent();

    // if ((isCreating || !titleRemainsTheSame) && titleAlreadyExists) {
    // Map<String, String> subMap = (Map<String, String>) errors.computeIfAbsent("title", k -> new HashMap<>());
    // subMap.put(tr.getLocale().getCode(), "Norādīts nosaukums jau eksistē.");
    // }
    // });

    // return errors;
    // }

    public static <T extends LocalableWithTitle> Map<String, Object> validateTitleUniqueness(
            HasTranslationMethod dto,
            Map<String, Object> errors,
            EntityExistenceProvider repoFunction,
            TranslatableEntity<T> forUpdate) {

        boolean isCreating = forUpdate == null;

        List<LocalableWithTitle> existingTranslations =
                isCreating ? null : forUpdate.getNarrowTranslations().stream().map(tr -> (LocalableWithTitle) tr).toList();

        dto.getTranslations().forEach(tr -> {
            String newTitle = tr.getTitle();
            LocaleCode forLocale = tr.getLocale();
            boolean titleAlreadyExists = repoFunction.existsByTitleAndLocale(newTitle, forLocale);
            boolean titleRemainsTheSame = !isCreating && existingTranslations
                    .stream()
                    .filter(existingTr -> existingTr.getLocale() == forLocale && newTitle.equals(existingTr.getTitle()))
                    .findFirst()
                    .isPresent();

            if ((isCreating || !titleRemainsTheSame) && titleAlreadyExists) {
                Map<String, String> subMap = (Map<String, String>) errors.computeIfAbsent("title", k -> new HashMap<>());
                subMap.put(tr.getLocale().getCode(), "Norādīts nosaukums jau eksistē.");
            }
        });

        return errors;
    }
}
