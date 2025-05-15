package lv.wings.service;

import lv.wings.model.entity.Order;
import lv.wings.model.security.User;

public interface EmailSenderService {
    void sendSimpleEmail(String to, String subject, String text);

    void sendOrderSuccessEmail(Order order);

    void sendLoginAttemptsExceeded(User user, String unlockUrl);

    void sendEmailToUnlockAccount(User user, String unlockUrl);

    void sendPasswordResetToken(User user, String resetUrl);

    void sendEmailChangeNotification(User user, String oldEmail, String newEmail);

    void sendPasswordChangeNotification(User user);

    void sendNewPasswordNotification(User user, String newPassword);

    void sendNewUsernameNotification(User user);

    void sendNewEmployeeNotification(User user, String newPassword);

    void sendAdminChangedPasswordNotification(User user, String newPassword);

    void sendOrderWasShippedEmail(Order order, String additionalComment);

    void sendOrderClosedEmail(Order order);
}
