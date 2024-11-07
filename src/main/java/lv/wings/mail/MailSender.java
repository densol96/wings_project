package lv.wings.mail;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public interface MailSender {
	public abstract JavaMailSender getJavaMailSender() throws Exception;
}
