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
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(order.getCustomer().getEmail());
            helper.setSubject(localService.getMessage("email.success.subject", new Object[] {order.getId()}));
            helper.setText(html, true);
            helper.setFrom("noreply@yourdomain.com");
            mailSender.send(mimeMessage);
            log.info("sendOrderSuccessEmail completed successfully!");
        } catch (Exception e) {
            log.error("MimeMessageException -> Unable to sendOrderSuccessEmail.");
        }

    }
}
