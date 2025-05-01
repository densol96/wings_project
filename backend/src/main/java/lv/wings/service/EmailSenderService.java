package lv.wings.service;

import lv.wings.model.entity.Order;
import lv.wings.model.security.User;

public interface EmailSenderService {
    void sendSimpleEmail(String to, String subject, String text);

    void sendOrderSuccessEmail(Order order);

    void sendLoginAttemptsExceeded(User user, String unlockUrl);

    void sendEmailToUnlockAccount(User user, String unlockUrl);
}
