package com.hytch.lfpspringmaster.sys.mail.service;

import com.hytch.lfpspringmaster.sys.mail.MessageContentBuilder;
import com.hytch.lfpspringmaster.sys.mail.model.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

/**
 * 邮件服务的实现类
 */
@Slf4j
@Service
@Async
public class MailServiceImpl implements MailService {

	private static final boolean ISHTML = true;
	private static final boolean ISMULTIPART = true;
	private final JavaMailSender mailSender;
	private final MessageContentBuilder contentBuilder;
	@Value("${spring.mail.username}")
	private String from;

	@Autowired
	public MailServiceImpl(
			JavaMailSender mailSender,
			MessageContentBuilder contentBuilder) {
		this.mailSender = mailSender;
		this.contentBuilder = contentBuilder;
	}

	@Override
	public void sendMailMessage(CustomMessage customMessage) throws MailException {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(from);
		simpleMailMessage.setTo((String[]) customMessage.getToPerson().toArray());
		simpleMailMessage.setSubject(customMessage.getSubject());
		simpleMailMessage.setText(customMessage.getContent());

		try {
			mailSender.send(simpleMailMessage);
		} catch (MailException e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public void sendHtml(CustomMessage customMessage) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(from);
			helper.setTo((String[]) customMessage.getToPerson().toArray());
			helper.setSubject(customMessage.getSubject());

			StringBuilder sb = new StringBuilder();
			sb.append("<h1>")
					.append(customMessage.getContent())
					.append("</h1>")
					.append("<p style='color:#F00'>红色字</p>")
					.append("<p style='text-align:right'>右对齐</p>");

			helper.setText(sb.toString(), true);
//			FileSystemResource file = new FileSystemResource(new File("D:/test/head/head1.jpg"));
//			helper.addInline("head",file);

			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendThymeleaf(
			CustomMessage customMessage, String template, List<Path> file) {
		final HashMap datas = new HashMap();
		MimeMessagePreparator preparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, ISMULTIPART);
			send(customMessage, file, messageHelper);
			messageHelper.setText(customMessage.getContent());
//			messageHelper.setText(contentBuilder.buildMessage(template, datas), ISHTML);
		};
		mailSender.send(preparator);
	}

	private void send(CustomMessage message,
										List<Path> attachments,
										MimeMessageHelper helper) throws MessagingException {
		try {

			List<String> personList = message.getToPerson();
			String[] persons = new String[personList.size()];
			personList.toArray(persons);
			helper.setFrom(from);
			helper.setTo(persons);
			helper.setSubject(message.getSubject());
			if (attachments != null && attachments.size() > 0) {
				for (Path path : attachments) {
					String fileName = path.getFileName().toString();
					String temp = fileName;
					int index = fileName.lastIndexOf(".");
					String suffix = fileName.substring(index + 1);
//					fileName = "附件上传." + suffix;
					helper.addAttachment(temp, path.toFile());
				}
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
