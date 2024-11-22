package lv.wings.mail.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lv.wings.mail.MailSender;

@Component
public class MailSenderImpl implements MailSender{
	@Autowired
    private JavaMailSender emailSender;

    @Value("${MAIL_DESTINATION_EMAIL}")
    private String destinationEmail;

    @Override
	public String getDestinationEmail(){
		return this.destinationEmail;
	}

    @Override
    public void sendMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("noreply@wings.com");
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
    }

    @Override
    public void sendMessage(String to, String subject, String text, String filename, ByteArrayResource attachement) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("noreply@wings.com");
        helper.setTo(to); 
        helper.setSubject(subject); 
        helper.setText(text);
        
        helper.addAttachment(filename, attachement);

        emailSender.send(message);
    }
}
