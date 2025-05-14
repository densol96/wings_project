package lv.wings.model.interfaces;

import lv.wings.model.security.User;

public interface ExtendedAuditable extends Auditable {
    User getCreatedBy();
}
