package lv.wings.model.interfaces;

import lv.wings.model.security.MyUser;

public interface ExtendedAuditable extends Auditable {
    MyUser getCreatedBy();
}
