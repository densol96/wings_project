package lv.wings.service;

import lv.wings.enums.SecurityEventType;
import lv.wings.model.security.User;

public interface SecurityEventService {
    void handleSecurityEvent(User user, SecurityEventType type, String additionalInfo);

    void doTheSessionAuditIfRequired(User user);
}
