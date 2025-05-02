package lv.wings.model.security;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.wings.enums.SecurityEventType;

@Entity
@Table(name = "security_events")
@Getter
@Setter
@NoArgsConstructor
public class SecurityEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private SecurityEventType eventType;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(length = 45)
    private String ipAddress;

    private String userAgent;

    private String requestUri;

    private String additionalInfo;

    @Builder
    public SecurityEvent(User user,
            SecurityEventType eventType,
            LocalDateTime dateTime,
            String ipAddress,
            String userAgent,
            String requestUri,
            String additionalInfo) {
        this.user = user;
        this.eventType = eventType;
        this.dateTime = (dateTime != null) ? dateTime : LocalDateTime.now();
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.requestUri = requestUri;
        this.additionalInfo = additionalInfo;
    }
}
