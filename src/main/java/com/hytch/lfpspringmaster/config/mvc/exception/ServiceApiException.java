package com.hytch.lfpspringmaster.config.mvc.exception;

/**
 * 自定义异常处理
 */
public class ServiceApiException extends RuntimeException {

	public ServiceApiException(String msg) {
		super(msg);
	}
}
