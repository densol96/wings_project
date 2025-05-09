package lv.wings.service;

import org.springframework.data.domain.Page;
import lv.wings.dto.response.admin.security_events.SecurityEventDto;
import lv.wings.enums.SecurityEventType;
import lv.wings.model.security.User;

public interface SecurityEventService {
    void handleSecurityEvent(User user, SecurityEventType type, String additionalInfo);

    void doTheSessionAuditIfRequired(User user);

    Page<SecurityEventDto> getSecurityEvents(Integer page, Integer size, String q, String securityEventType);
}
