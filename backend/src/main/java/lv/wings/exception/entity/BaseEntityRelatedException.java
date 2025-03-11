package lv.wings.exception.entity;

import lombok.Getter;

@Getter
public class BaseEntityRelatedException extends RuntimeException {

    private final String entityNameKey;
    private final String actualEntityName;
    private final Object entityId;

    /*
     * problemMessage = something like "not found" or "doesn't have a valid translation" or some other entity related exception
     * 
     * Exception messages will be logged in ExceptionAdvice and i18n will take place there as well by using entityNameKey
     */
    public BaseEntityRelatedException(String entityNameKey, String actualEntityName, Object entityId, String problemMessage) {
        super(actualEntityName + " with id " + entityId + " " + problemMessage);
        this.entityNameKey = entityNameKey; // for i18n
        this.actualEntityName = actualEntityName;
        this.entityId = entityId;
    }
}
