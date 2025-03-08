package lv.wings.exception.entity;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private final String entityNameKey;
    private final String actualEntityName;
    private final Object entityId;

    public EntityNotFoundException(String entityNameKey, String actualEntityName, Object entityId) {
        super(actualEntityName + " with id " + entityId + " not found");
        this.entityNameKey = entityNameKey;
        this.actualEntityName = actualEntityName;
        this.entityId = entityId;
    }
}
