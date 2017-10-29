package com.hytch.lfpspringmaster.config.mvc.data;

import com.hytch.lfpspringmaster.base.BaseModel;
import com.hytch.lfpspringmaster.config.mvc.api.WebApi;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一返回数据给api调用者.
 */
@ControllerAdvice
public class DataResponseBodyAdvice implements ResponseBodyAdvice {

	@Override
	public boolean supports(MethodParameter methodParameter, Class aClass) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType,
																Class aClass, ServerHttpRequest serverHttpRequest,
																ServerHttpResponse serverHttpResponse) {
		if (methodParameter.hasMethodAnnotation(ExceptionHandler.class)) {//处理异常，可以再添加一个异常处理的类，用于处理异常返回格式

			return body;
		} else if (methodParameter.hasMethodAnnotation(WebApi.class)) {
			BaseModel model = (BaseModel) body;
			ServletServerHttpResponse response = (ServletServerHttpResponse) serverHttpResponse;
			ResponseTemplate template = new ResponseTemplate();

			switch (model.getCode()) {
				case -3:
					template.setMessage(model.getMessage());
					if (model.getMessage() == null) {
						template.setMessage("数据为空");
					}
					break;
				default:
					template.setData(body);
					template.setMessage("获取数据成功");
					break;
			}
			template.setCode(model.getCode());
			if (model.getCode() == 0) {
				template.setCode(response.getServletResponse().getStatus());
			}

			return template;
		} else {
			ResponseTemplate template = new ResponseTemplate();
			template.setData(body);

			return template;
		}

//		return body;
	}
}
