package lv.wings.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class EmailConfiguration {
	@Value("${MAIL_HOST}")
    private String host;
	
	@Value("${MAIL_PORT}")
    private int port;
	
	@Value("${MAIL_USERNAME}")
    private String username;

	@Value("${MAIL_PASSWORD}")
    private String password;

	@Bean
	public JavaMailSender getJavaMailSender() throws Exception{
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(host);
		mailSender.setPort(port);
		mailSender.setUsername(username);
		mailSender.setPassword(password);
		
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");
		
		return mailSender;
	}
}
