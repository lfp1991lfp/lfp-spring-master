package com.hytch.lfpspringmaster.config.mvc.data;

import com.hytch.lfpspringmaster.base.BaseModel;
import com.hytch.lfpspringmaster.base.Result;
import com.hytch.lfpspringmaster.config.mvc.api.WebApi;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Collection;

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
		}
		if (methodParameter.hasMethodAnnotation(WebApi.class)) {
			return value(body, serverHttpResponse);
		}

		Class aClass1 = methodParameter.getDeclaringClass();
		if (aClass1 != null &&
				AnnotationUtils.isAnnotationDeclaredLocally(WebApi.class, aClass1)) {
			return value(body, serverHttpResponse);
		}
		return body;
	}

	private Object value(Object body, ServerHttpResponse serverHttpResponse) {
		ServletServerHttpResponse response = (ServletServerHttpResponse) serverHttpResponse;
		//自定义结果bean
		if (body instanceof Result) {
			ResponseTemplate template = new ResponseTemplate();
			template.setCode(response.getServletResponse().getStatus());
			template.setData(body);

			return template;
		}
		if (body instanceof Collection) {
			ResponseTemplate template = new ResponseTemplate();
			template.setCode(response.getServletResponse().getStatus());
			template.setData(body);
			if (((Collection) body).size() == 0) {
				template.setCode(-3);
				template.setMessage("数据为空");
			} else {
				template.setMessage("获取成功");
			}

			return template;
		}
		if (body instanceof BaseModel) {
			BaseModel model = (BaseModel) body;
			ResponseTemplate template = new ResponseTemplate();

			template.setCode(model.getCode());
			switch (model.getCode()) {
				case -3:
					template.setMessage(model.getMessage());
					if (model.getMessage() == null) {
						template.setMessage("数据为空");
					}
					break;
				case 0:
					template.setCode(response.getServletResponse().getStatus());
					break;
				default:
					template.setData(body);
					template.setMessage("获取数据成功");
					break;
			}

			return template;
		}

		return body;
	}
}
