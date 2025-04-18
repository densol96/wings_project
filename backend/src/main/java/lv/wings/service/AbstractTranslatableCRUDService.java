package lv.wings.service;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.jpa.repository.JpaRepository;
import lv.wings.enums.LocaleCode;
import lv.wings.exception.entity.MissingTranslationException;
import lv.wings.model.base.LocalableEntity;
import lv.wings.model.interfaces.Translatable;

public abstract class AbstractTranslatableCRUDService<T extends Translatable, L extends LocalableEntity<T>, ID>
        extends AbstractCRUDService<T, ID> {

    protected final LocaleService localeService;

    protected AbstractTranslatableCRUDService(JpaRepository<T, ID> repository, String entityName, String entityNameKey, LocaleService localeService) {
        super(repository, entityName, entityNameKey);
        this.localeService = localeService;
    }

    protected L getRightTranslation(T entity, Class<L> translationClass) {
        return localeService.getRightTranslation(
                entity,
                translationClass,
                () -> new MissingTranslationException(
                        entityNameKey,
                        entityName,
                        entity.getId()));
    }

    protected L getRightTranslationForSelectedLocale(T entity, Class<L> translationClass, LocaleCode localCode) {
        return localeService.getTranslationPerSelectedLocale(
                entity,
                translationClass,
                () -> new MissingTranslationException(
                        entityNameKey,
                        entityName,
                        entity.getId()),

                localCode);
    }
}
