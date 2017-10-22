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
//		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
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
	}

	@Bean
	public Filter shallowETagHeaderFilter() {
		return new ShallowEtagHeaderFilter();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CacheControlInterceptor());
	}
}
