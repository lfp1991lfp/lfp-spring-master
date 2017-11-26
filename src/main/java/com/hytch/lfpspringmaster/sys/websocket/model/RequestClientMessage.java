package com.hytch.lfpspringmaster.sys.websocket.model;

import lombok.Data;

/**
 * 收到浏览器（客户端）的请求消息model
 */
@Data
public class RequestClientMessage {
	private String name;
}
