package com.hytch.lfpspringmaster.sys.websocket.controller;

import com.hytch.lfpspringmaster.sys.websocket.model.RequestClientMessage;
import com.hytch.lfpspringmaster.sys.websocket.model.ResponseClientMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * webSocket控制层
 */
@Controller
public class WsController {

	/**
	 * MessageMapping表示消息映射的URL路径,
	 * SendTo注解表示当服务器有消息需要推送的时候,
	 * 会对订阅了@SendTo中路径的浏览器发送消息
	 */
	@MessageMapping("/welcome")
	@SendTo("/topic/getResponse")
	public ResponseClientMessage say(RequestClientMessage message) {
		System.out.println(message.getName());
		return new ResponseClientMessage("welcome," + message.getName() + " !");
	}

}
