package com.hytch.lfpspringmaster.config.swagger2;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.base.Predicates.or;
import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@ApiIgnore
@Profile("dev")
public class Swagger2Config {
	
	private final TypeResolver typeResolver;

	@Autowired
	public Swagger2Config(TypeResolver typeResolver) {
		this.typeResolver = typeResolver;
	}

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("api-v1")
				.select()
				.apis(RequestHandlerSelectors.any())   //把所有扫描到的api都显示出来
				.paths(or(regex("/api/.*")))  //匹配路径为/api/.*的api显示到doc文档上
				.build()
//				.pathProvider()     //这个主要是修改项目的访问地址
				.apiInfo(apiInfo());
//				.pathMapping("/")
//				.directModelSubstitute(LocalDate.class, String.class)
//				.genericModelSubstitutes(ResponseEntity.class)
//				.alternateTypeRules(
//						newRule(typeResolver.resolve(DeferredResult.class,
//								typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
//								typeResolver.resolve(WildcardType.class)))
//				.useDefaultResponseMessages(false)
//				.globalResponseMessage(RequestMethod.GET,
//						newArrayList(new ResponseMessageBuilder()
//								.code(500)
//								.message("500 message")
//								.responseModel(new ModelRef("Error"))
//								.build()))
////				.securitySchemes(newArrayList(apiKey()))
////				.securityContexts(newArrayList(securityContext()))
//				.enableUrlTemplating(true);
//				.globalOperationParameters(
//						newArrayList(new ParameterBuilder()
//								.name("token")
//								.description("获取用户令牌")
//								.modelRef(new ModelRef("string"))
//								.parameterType("query")
//								.required(true)
//								.build()))
	}

	private ApiKey apiKey() {
		return new ApiKey("mykey", "api_keys", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(regex("/anyPath.*"))
				.build();
	}
	
	//
//	@Bean
//	SecurityConfiguration security() {
//		return new SecurityConfiguration("abc", "123", "pets",
//				"petstore", "123",
//				ApiKeyVehicle.HEADER, "", ",");
//	}
	//Here is an example where we select any api that matches one of these paths
//	protected Predicate<String> paths() {
//		return or(
//				regex("/business.*"),
//				regex("/some.*"),
//				regex("/contacts.*"),
//				regex("/demo/*.*"),
//				regex("/springsRestController.*"),
//				regex("/test.*"));
//	}
//
	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope
				= new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		
		return newArrayList(new SecurityReference("mykey", authorizationScopes));
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("用户管理系统")
				.description("Restful API文档")
				.contact(new Contact("lfp", "http://xxx.zz.com", "admin@email.com"))
				.version("2.0")
				.build();
	}

//	@Bean
//	UiConfiguration uiConfig() {
//		return new UiConfiguration(
//				null,// url
//				"none",       // docExpansion          => none | list（是否展开）
//				"alpha",      // apiSorter             => alpha
//				"schema",     // defaultModelRendering => schema
//				UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
//				false,        // enableJsonEditor      => true | false
//				true,         // showRequestHeaders    => true | false
//				60000L);      // requestTimeout => in milliseconds, defaults to null (uses jquery xh timeout)
//	}
}
