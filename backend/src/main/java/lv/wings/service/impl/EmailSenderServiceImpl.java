package lv.wings.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        String html = emailTemplateService.generateLoginAttemptsExceededEmailHtml(user, requestUnlockUrl);
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

    private void sendHtmlEmail(String to, String subject, String html) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
            helper.setFrom(fromEmail);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Unable to send email to {} with subject '{}'", to, subject, e);
            throw new RuntimeException(e.getMessage()); // will be ahndled as a procedural exception
        }
    }


}
