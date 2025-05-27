package lv.wings.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.enums.LocaleCode;
import lv.wings.model.entity.Customer;
import lv.wings.model.entity.Order;
import lv.wings.model.security.User;
import lv.wings.service.EmailSenderService;
import lv.wings.service.EmailTemplateService;
import lv.wings.service.LocaleService;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender mailSender;
    private final EmailTemplateService emailTemplateService;
    private final LocaleService localService;

    @Value("${from.email}")
    private String fromEmail;

    // @PostConstruct
    public void testConnection() throws Exception {
        try {
            sendSimpleEmail("deniss11sol@gmail.com", "Test", "Connection OK");
            System.out.println("✅ Email test sent successfully!");
        } catch (Exception e) {
            System.err.println("❌ Email sending failed: " + e.getMessage());
        }
    }

    @Override
    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Override
    public void sendOrderSuccessEmail(Order order) {
        String html = emailTemplateService.generateSuccessfulPaymentEmailHtml(order);
        String subject = localService.getMessage("email.success.subject", new Object[] {order.getId()});
        sendHtmlEmail(order.getCustomer().getEmail(), subject, html);
    }

    @Override
    public void sendLoginAttemptsExceeded(User user, String requestUnlockUrl) {
        System.out.println("=== I RUN ===");
        String html = emailTemplateService.generateLoginAttemptsExceededEmailHtml(user, requestUnlockUrl);
        System.out.println(html);
        System.out.println(html);
        System.out.println(user.getEmail());
        sendHtmlEmail(user.getEmail(), "Darba konta bloķēšana", html);
    }

    @Override
    public void sendEmailToUnlockAccount(User user, String unlockUrl) {
        String html = emailTemplateService.generateUnlockAccountEmailHtml(user, unlockUrl);
        sendHtmlEmail(user.getEmail(), "Darba konta atbloķēšana", html);
    }

    @Override
    public void sendPasswordResetToken(User user, String resetUrl) {
        String html = emailTemplateService.generateResetPasswordEmailHtml(user, resetUrl);
        sendHtmlEmail(user.getEmail(), "Pieprasījums paroles maiņai", html);
    }

    @Override
    public void sendEmailChangeNotification(User user, String oldEmail, String newEmail) {
        String html = emailTemplateService.generateEmailWasChangedHtml(user, oldEmail, newEmail);
        sendHtmlEmail(oldEmail, "E-pasta adreses maiņa", html);
        sendHtmlEmail(newEmail, "E-pasta adreses maiņa", html);
    }

    @Override
    public void sendPasswordChangeNotification(User user) {
        String html = emailTemplateService.generatePasswordWasChangedHtml(user);
        sendHtmlEmail(user.getEmail(), "Parole tika nomainīta", html);
    }

    @Override
    public void sendNewPasswordNotification(User user, String newPassword) {
        String html = emailTemplateService.generateNewPasswordHtml(user, newPassword);
        sendHtmlEmail(user.getEmail(), "Parole tika nomainīta", html);
    }

    @Override
    public void sendNewUsernameNotification(User user) {
        String html = emailTemplateService.generateNewUsernameHtml(user);
        sendHtmlEmail(user.getEmail(), "Lietotājvārds tika nomainīts", html);
    }

    @Override
    public void sendNewEmployeeNotification(User user, String newPassword) {
        String html = emailTemplateService.generateNewEmployeeHtml(user, newPassword);
        sendHtmlEmail(user.getEmail(), "Jauna lietotāja konts", html);
    }


    @Override
    public void sendAdminChangedPasswordNotification(User user, String newPassword) {
        String html = emailTemplateService.generateAdminChangedPasswordHtml(user, newPassword);
        sendHtmlEmail(user.getEmail(), "Paroles maiņa", html);
    }

    @Override
    public void sendOrderWasShippedEmail(Order order, String additionalComment) {
        Customer customer = order.getCustomer();
        LocaleCode locale = order.getLocale();
        String html = emailTemplateService.generateOrderWasSentHtml(customer, additionalComment, locale);
        String subject = localService.getMessageForSpecificLocale("email.order-sent", new Object[] {order.getId()}, locale);
        sendHtmlEmail(customer.getEmail(), subject, html);
    }

    @Override
    public void sendOrderClosedEmail(Order order) {
        String html = emailTemplateService.generateOrderClosedHtml(order);
        String subject = localService.getMessageForSpecificLocale("email.order-closed", new Object[] {order.getId()}, order.getLocale());
        sendHtmlEmail(order.getCustomer().getEmail(), subject, html);
    }


    private void sendHtmlEmail(String to, String subject, String html) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
            helper.setFrom(fromEmail);
            mailSender.send(message);
            log.info("Email sent to {} with subject '{}'", to, subject);
        } catch (Exception e) {
            log.error("Unable to send email to {} with subject '{}'", to, subject, e);
            throw new RuntimeException(e.getMessage()); // will be ahndled as a procedural exception
        }
    }
}
