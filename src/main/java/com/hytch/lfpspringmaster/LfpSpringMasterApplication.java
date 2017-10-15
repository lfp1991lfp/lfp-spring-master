package com.hytch.lfpspringmaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(
		exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class},
		scanBasePackages = {"com.hytch.lfpspringmaster"})
//如果不使用@ComponentScan指明对象扫描范围，默认指扫描当前启动类所在的包里的对象
@ServletComponentScan(basePackages = "com.hytch.lfpspringmaster")
@ComponentScan(basePackages = "com.hytch.lfpspringmaster")//组件扫描,若basePackages不配，// 则默认所在的包名
public class LfpSpringMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(LfpSpringMasterApplication.class, args);
	}
}
