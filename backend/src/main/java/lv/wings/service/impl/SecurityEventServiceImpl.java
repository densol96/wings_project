package lv.wings.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.enums.SecurityEventType;
import lv.wings.model.security.SecurityEvent;
import lv.wings.model.security.User;
import lv.wings.repo.SecurityEventRepository;
import lv.wings.service.EmailSenderService;
import lv.wings.service.SecurityEventService;
import lv.wings.service.UserService;
import lv.wings.util.RequestUtils;
import lv.wings.util.UrlAssembler;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityEventServiceImpl implements SecurityEventService {

    private final SecurityEventRepository securityEventRepository;
    private final UserService userService;
    private final EmailSenderService emailSenderService;

    private final int MAX_LOGIN_ATTEMPTS = 5;
    private final int WORK_START_TIME = 8;
    private final int WORK_FINISH_TIME = 18;

    @Override
    public void handleSecurityEvent(@NonNull User user, @NonNull SecurityEventType type, String additionalInfo) {
        HttpServletRequest request = RequestUtils.getCurrentServletRequest();
        String ipAddress = RequestUtils.getClientIp(request);
        String userAgent = request.getHeader("User-Agent");
        String requestUri = request.getRequestURI();

        doConsoleLogging(user.getUsername(), ipAddress, userAgent, requestUri, type);

        createAndSaveSecurityEvent(user, type, ipAddress, userAgent, requestUri, additionalInfo);

        if (type == SecurityEventType.LOGIN_SUCCESS) {
            user.setLoginAttempts(0);
            doIpCheck(ipAddress, user);
            doUserAgentCheck(userAgent, user);
            doAfterHoursAccessCheck(user);
        } else if (type == SecurityEventType.LOGIN_FAILED) {
            user.setLoginAttempts(user.getLoginAttempts() + 1);
            if (user.getLoginAttempts() >= MAX_LOGIN_ATTEMPTS) {
                user.setActive(false);
                emailSenderService.sendLoginAttemptsExceeded(user, UrlAssembler.getFullFrontendPath("/request-account-unlock"));
            }
        } else if (type == SecurityEventType.ACCESS_FROM_NEW_IP) {
            user.setLastIpAddress(ipAddress);
        } else if (type == SecurityEventType.UNUSUAL_USER_AGENT) {
            user.setLastUserAgent(userAgent);
        }
        userService.persist(user);
        /*
         * Other possible SecurityEventType: AFTER_HOURS_ACCESS, PASSWORD_CHANGED, TOKEN_INVALID
         * Do not require anything else for now apart from the logging above..
         */
    }

    @Override
    public void doTheSessionAuditIfRequired(@NonNull User user) {
        HttpServletRequest request = RequestUtils.getCurrentServletRequest();
        String ipAddress = RequestUtils.getClientIp(request);
        String userAgent = request.getHeader("User-Agent");
        doIpCheck(ipAddress, user);
        doUserAgentCheck(userAgent, user);
        doAfterHoursAccessCheck(user);
    }

    private void doIpCheck(String ipAddress, User user) {
        if (!ipAddress.equals(user.getLastIpAddress())) {
            handleSecurityEvent(user, SecurityEventType.ACCESS_FROM_NEW_IP, null);
        }
    }

    private void doUserAgentCheck(String userAgent, User user) {
        if (!userAgent.equals(user.getLastUserAgent())) {
            handleSecurityEvent(user, SecurityEventType.UNUSUAL_USER_AGENT, null);
        }
    }

    private void doAfterHoursAccessCheck(User user) {
        int currentHour = LocalDateTime.now().getHour();
        if ((currentHour < WORK_START_TIME || currentHour > WORK_FINISH_TIME) && shouldLogAfterHoursAccess(user)) {
            handleSecurityEvent(user, SecurityEventType.AFTER_HOURS_ACCESS, null);
        }
    }

    private boolean shouldLogAfterHoursAccess(User user) {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        return !securityEventRepository.existsByUserAndEventTypeAndDateTimeAfter(
                user, SecurityEventType.AFTER_HOURS_ACCESS, oneHourAgo);
    }

    private void doConsoleLogging(String username, String ipAddress, String userAgent, String requestUri, SecurityEventType type) {
        log.warn("=== SECURITY EVENT DETECTED ===");
        log.warn("Security Event Type - {}", type.name());
        log.warn("Date/time - {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        log.warn("User - {}", username);
        log.warn("ipAddress - {}", ipAddress);
        log.warn("userAgent - {}", userAgent);
        log.warn("requestUri - {}", requestUri);
        log.warn("==================");
    }

    private void createAndSaveSecurityEvent(User user, SecurityEventType type, String ipAddress, String userAgent, String requestUri,
            String additionalInfo) {
        SecurityEvent event = SecurityEvent.builder()
                .user(user)
                .eventType(type)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .requestUri(requestUri)
                .additionalInfo(additionalInfo)
                .dateTime(LocalDateTime.now())
                .build();
        securityEventRepository.save(event);
    }
}
