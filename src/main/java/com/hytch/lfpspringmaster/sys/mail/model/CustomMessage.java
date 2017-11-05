package com.hytch.lfpspringmaster.sys.mail.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 发送邮件的内容.
 */
@Data
@NoArgsConstructor
public class CustomMessage {

	private List<String> toPerson;   //发给谁
	private String subject;    //发送主题
	private String content;    //发送内容
}
