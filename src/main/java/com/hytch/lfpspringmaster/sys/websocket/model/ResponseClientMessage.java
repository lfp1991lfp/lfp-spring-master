package com.hytch.lfpspringmaster.sys.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 服务器响应的请求消息
 */
@Data
@AllArgsConstructor
public class ResponseClientMessage {
	private String responseMessage;
}
