package com.hytch.lfpspringmaster.sys.mail.service;

import com.hytch.lfpspringmaster.sys.mail.model.CustomMessage;

import java.nio.file.Path;
import java.util.List;

/**
 * 管理邮件发送的服务.
 */
public interface MailService {

	/**
	 * 发送邮件的信息.
	 *
	 * @param customMessage 邮件信息
	 */
	void sendMailMessage(CustomMessage customMessage);

	/**
	 * 发送邮件中含有html.
	 *
	 * @param customMessage 邮件信息
	 */
	void sendHtml(CustomMessage customMessage);

	/**
	 * 使用thymeleaf模版当作邮件模版.
	 *
	 * @param customMessage 邮件相关信息(收件人、邮件主题、邮件内容)
	 * @param template      (使用的模版)
	 * @param files         附件路径地址
	 */
	void sendThymeleaf(CustomMessage customMessage, String template, List<Path> files);
}
