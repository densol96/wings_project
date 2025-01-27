package lv.wings.mail;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

@Service   
public interface MailSender {
	//public abstract JavaMailSender getJavaMailSender() throws Exception;
	public abstract void sendMessage(String to, String subject, String text);
	public abstract void sendMessage(String to, String subject, String text, String filename, ByteArrayResource attachement) throws MessagingException;
	public abstract String getDestinationEmail();
}
