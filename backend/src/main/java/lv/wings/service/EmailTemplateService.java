package lv.wings.service;

import lv.wings.model.entity.Order;
import lv.wings.model.security.User;

public interface EmailTemplateService {
    String generateSuccessfulPaymentEmailHtml(Order order);

    String generateLoginAttemptsExceededEmailHtml(User user, String unlockUrl);

    String generateUnlockAccountEmailHtml(User user, String unlockUrl);
}
