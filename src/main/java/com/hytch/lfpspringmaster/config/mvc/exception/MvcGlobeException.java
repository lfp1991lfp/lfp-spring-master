package com.hytch.lfpspringmaster.config.mvc.exception;

import com.hytch.lfpspringmaster.config.mvc.data.ResponseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * mvc全局异常捕获
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class MvcGlobeException {

	/**
	 * 500 - Internal Server Error
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ServiceApiException.class)
	public ResponseTemplate handleServiceException(ServiceApiException e) {
		log.error("常规错误", e.getCause());

		return responseTemplate(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getCause().getMessage());
	}

//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler(RuntimeException.class)
//	public ResponseTemplate handlerDaoException(DataIntegrityViolationException e) {
//		log.error("数据库操作错误＝" + e.getCause());  //写入文件
//		return responseTemplate(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getCause().getMessage());
//	}

	private ResponseTemplate responseTemplate(int code, String message) {
		ResponseTemplate template = new ResponseTemplate();
		template.setCode(code);
		template.setMessage(message);

		return template;
	}
}
