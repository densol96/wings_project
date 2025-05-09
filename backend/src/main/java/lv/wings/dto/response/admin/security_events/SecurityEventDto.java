package lv.wings.dto.response.admin.security_events;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SecurityEventDto {
    private Integer id;
    private String username;
    private String eventType;
    private LocalDateTime dateTime;
    private String ipAddress;
    private String userAgent;
    private String requestUri;
    private String additionalInfo;
}
