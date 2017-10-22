package com.hytch.lfpspringmaster.config.mvc.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor//生成包含所有变量的有参构造函数
public class ResponseTemplate {

	Object data;

	String message;

	int code;
}
