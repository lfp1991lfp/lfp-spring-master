package com.hytch.lfpspringmaster.config.mvc;

import com.hytch.lfpspringmaster.config.mvc.interceptor.CacheControlInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.Filter;
import java.util.concurrent.TimeUnit;

/**
 * 配置mvc配置
 * EnableWebMvc加上这个会影响jackson的格式化输出,json数据会格式化不了
 */
@Configuration
public class ApplicationConfigMVC extends WebMvcConfigurerAdapter {

	/**
	 * 发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。
	 * 需要重新指定静态资源
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
				.addResourceHandler("/**")
				.addResourceLocations("classpath:/static/");
		//配置swagger日志的访问
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/")
				.setCacheControl(CacheControl
						.maxAge(365, TimeUnit.DAYS)
						.cachePublic());
		super.addResourceHandlers(registry);
	}

	/**
	 * 配置servlet处理
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		super.configurePathMatch(configurer);
		configurer
				.setUseSuffixPatternMatch(false)    //禁止使用带后缀的地址
				.setUseTrailingSlashMatch(true);    //不区分末尾是否带斜杠的链接，比如http://host:port/a和http://host:port/a/一致
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/api-docs/").setViewName("redirect:swagger-ui.html");
		registry.addViewController("/ws").setViewName("/ws");  //ws.html的映射路径
	}

	@Bean
	public Filter shallowETagHeaderFilter() {
		return new ShallowEtagHeaderFilter();
	}

	//代码设置文件路径 Servlet3.0
//	@Bean
//	MultipartConfigElement multipartConfigElement() {
//		MultipartConfigFactory factory = new MultipartConfigFactory();
//		factory.setUpload("/app/pttms/tmp");
//		return factory.createMultipartConfig();
//	}

	//显示声明CommonsMultipartResolver为mutipartResolver 老的方法Jakarta Commons FileUpload
//	@Bean(name = "multipartResolver")
//	public MultipartResolver multipartResolver() throws IOException {
//		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//		resolver.setDefaultEncoding("UTF-8");
//		resolver.setResolveLazily(true);//resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
//		resolver.setMaxInMemorySize(40960);
//		resolver.setMaxUploadSize(50 * 1024 * 1024);//上传文件大小 50M 50*1024*1024
//		resolver.setUploadTempDir(new FileSystemResource("/upload/tmp"));
//
//		return resolver;
//	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CacheControlInterceptor());
	}
}
