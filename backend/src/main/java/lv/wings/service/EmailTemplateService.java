package lv.wings.service;

import lv.wings.enums.LocaleCode;
import lv.wings.model.entity.Customer;
import lv.wings.model.entity.Order;
import lv.wings.model.security.User;

public interface EmailTemplateService {
    String generateSuccessfulPaymentEmailHtml(Order order);

    String generateLoginAttemptsExceededEmailHtml(User user, String unlockUrl);

    String generateUnlockAccountEmailHtml(User user, String unlockUrl);

    String generateResetPasswordEmailHtml(User user, String resetUrl);

    String generateEmailWasChangedHtml(User user, String oldEmail, String newEmail);

    String generatePasswordWasChangedHtml(User user);

    String generateNewPasswordHtml(User user, String newPassword);

    String generateNewUsernameHtml(User user);

    String generateNewEmployeeHtml(User user, String newPassword);

    String generateAdminChangedPasswordHtml(User user, String newPassword);

    String generateOrderWasSentHtml(Customer customer, String additionalComment, LocaleCode locale);

    String generateOrderClosedHtml(Order order);
}
