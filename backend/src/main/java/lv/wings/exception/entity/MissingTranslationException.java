package lv.wings.exception.entity;

public class MissingTranslationException extends BaseEntityRelatedException {
    public MissingTranslationException(String entityNameKey, String actualEntityName, Integer entityId) {
        super(entityNameKey, actualEntityName, entityId, "is missing a valid translation.");
    }
}
