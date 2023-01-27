package com.supermarket.api.service.GlobalService;

import java.nio.charset.StandardCharsets;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.supermarket.api.entity.User;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private Environment env;

	public void sent(User user, String code, String subject) {
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			helper.setFrom(new InternetAddress(env.getProperty("spring.mail.username"), "Aus SuperMarket"));
			helper.setTo(user.getEmail());
			helper.setSubject(subject);

			emailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendEmail(User user, String code, String subject) {
		Runnable emailSender = () -> {
			this.sent(user, code, subject);
		};
		Thread sendThread = new Thread(emailSender);
		sendThread.start();
	}
}
