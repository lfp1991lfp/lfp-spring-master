package com.hytch.lfpspringmaster.sys.mail;

import java.util.Map;

public interface MessageContentBuilder {
	String buildMessage(String templateName, Map<String, Object> datas);
}
