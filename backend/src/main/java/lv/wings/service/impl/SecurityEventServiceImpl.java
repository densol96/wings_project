package lv.wings.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import io.micrometer.common.lang.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.config.security.UserSecurityService;
import lv.wings.dto.response.admin.security_events.SecurityEventDto;
import lv.wings.enums.RedisKeyType;
import lv.wings.enums.SecurityEventType;
import lv.wings.exception.validation.InvalidParameterException;
import lv.wings.mapper.SecurityEventMapper;
import lv.wings.model.security.SecurityEvent;
import lv.wings.model.security.User;
import lv.wings.repo.SecurityEventRepository;
import lv.wings.repo.UserRepository;
import lv.wings.service.EmailSenderService;
import lv.wings.service.SecurityEventService;
import lv.wings.service.TokenStoreService;
import lv.wings.util.HashUtils;
import lv.wings.util.RequestUtils;
import lv.wings.util.UrlAssembler;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityEventServiceImpl implements SecurityEventService {

    private final SecurityEventRepository securityEventRepository;
    private final UserRepository userRepo;
    private final EmailSenderService emailSenderService;
    private final UserSecurityService userSecurityService;
    private final TokenStoreService tokenStoreService;
    private final SecurityEventMapper mapper;

    private final int MAX_LOGIN_ATTEMPTS = 5;
    private final int WORK_START_TIME = 8;
    private final int WORK_FINISH_TIME = 18;
    private final int ACTIVTY_TIMEOUT_MINUTES = 30; // no point updating the DB on every request. 15 minute interval is better

    @Override
    public void handleSecurityEvent(@NonNull User user, @NonNull SecurityEventType type, @Nullable String additionalInfo) {
        HttpServletRequest request = RequestUtils.getCurrentServletRequest();
        String ipAddress = RequestUtils.getClientIp(request);
        String userAgent = request.getHeader("User-Agent");
        String requestUri = request.getRequestURI();

        doConsoleLogging(user.getUsername(), ipAddress, userAgent, requestUri, type);

        switch (type) {
            case NEW_USER_REGISTERED -> {
                User adminUser = userSecurityService.getCurrentUserDetails().getUser(); // who is adding a new user
                additionalInfo = String.format("Jaunu lietotāju pievienoja %s", adminUser.getUsername());
            }

            case LOGIN_SUCCESS -> {
                user.setLoginAttempts(0);
                doIpCheck(ipAddress, user);
                doUserAgentCheck(userAgent, user);
                doAfterHoursAccessCheck(user);
            }

            case LOGIN_FAILED -> {
                user.setLoginAttempts(user.getLoginAttempts() + 1);
                if (user.getLoginAttempts() >= MAX_LOGIN_ATTEMPTS && !user.isAccountLocked() && !user.isAccountBanned()) {
                    user.setAccountLocked(true);
                    String token = HashUtils.createRandomToken();
                    String hashedToken = HashUtils.createTokenHash(token);
                    tokenStoreService.storeToken(RedisKeyType.REQUEST_UNLOCK, hashedToken, user.getId());
                    System.out.println("SENDING.......");
                    emailSenderService.sendLoginAttemptsExceeded(
                            user,
                            UrlAssembler.getFullFrontendPath("/admin/request-account-unlock/" + token));
                    handleSecurityEvent(user, SecurityEventType.ACCOUNT_LOCKED, null);
                }
            }

            case ACCESS_FROM_NEW_IP -> user.setLastIpAddress(ipAddress);

            case UNUSUAL_USER_AGENT -> user.setLastUserAgent(userAgent);
        }
        if (type == SecurityEventType.LOGIN_SUCCESS || type == SecurityEventType.LOGIN_FAILED || type == SecurityEventType.ACCESS_FROM_NEW_IP
                || type == SecurityEventType.UNUSUAL_USER_AGENT) {
            userRepo.save(user);
        }

        createAndSaveSecurityEvent(user, type, ipAddress, userAgent, requestUri, additionalInfo);
        /*
         * Other possible SecurityEventType:
         * PASSWORD_CHANGED,
         * PASSWORD_RESET,
         * AFTER_HOURS_ACCESS,
         * ACCOUNT_LOCKED,
         * ACCOUNT_UNLOCKED,
         * ACCOUNT_BANNED,
         * ACCOUNT_UNBANNED
         * Do not require anything else for now apart from the logging above..
         */
    }

    @Override
    public Page<SecurityEventDto> getSecurityEvents(Integer page, Integer size, String q, String securityEventType) {
        Page<SecurityEvent> data;

        SecurityEventType type = null;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("dateTime").descending());

        if (securityEventType != null && !securityEventType.isBlank()) {
            try {
                type = SecurityEventType.valueOf(securityEventType);
            } catch (IllegalArgumentException e) {
                throw new InvalidParameterException("security.event-type", securityEventType, false);
            }
        }

        if (q != null && !q.isBlank()) {
            data = type == null ? securityEventRepository.search(q, pageable) : securityEventRepository.search(q, type, pageable);
        } else {
            data = type == null ? securityEventRepository.findAll(pageable) : securityEventRepository.findAllByEventType(type, pageable);
        }

        return data.map(mapper::toDto);
    }

    @Override
    public void doTheSessionAuditIfRequired(@NonNull User user) {
        HttpServletRequest request = RequestUtils.getCurrentServletRequest();
        String ipAddress = RequestUtils.getClientIp(request);
        String userAgent = request.getHeader("User-Agent");
        doIpCheck(ipAddress, user);
        doUserAgentCheck(userAgent, user);
        doAfterHoursAccessCheck(user);
        doActivityRecordCheck(user);
    }

    private void doActivityRecordCheck(User user) {
        LocalDateTime lastTimeActive = user.getLastActivityDateTime();
        LocalDateTime now = LocalDateTime.now();
        if (lastTimeActive == null || now.minusMinutes(ACTIVTY_TIMEOUT_MINUTES).isAfter(lastTimeActive)) {
            userRepo.updateLastActivity(user.getId(), LocalDateTime.now());
        }
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
