package lv.wings.model.interfaces;

import java.time.LocalDateTime;
import lv.wings.model.security.User;

public interface Auditable {
    Integer getId();

    LocalDateTime getCreatedAt();

    LocalDateTime getLastModifiedAt();

    User getLastModifiedBy();
}
