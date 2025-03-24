package lv.wings.exception.entity;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends BaseEntityRelatedException {

    public EntityNotFoundException(String entityNameKey, String actualEntityName, Object entityId) {
        super(entityNameKey, actualEntityName, entityId, "is not found.");
    }
}
