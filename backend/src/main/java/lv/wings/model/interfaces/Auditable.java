package lv.wings.model.interfaces;

import java.time.LocalDateTime;
import lv.wings.model.security.MyUser;

public interface Auditable {
    Integer getId();

    LocalDateTime getCreatedAt();

    LocalDateTime getLastModifiedAt();

    MyUser getLastModifiedBy();
}
