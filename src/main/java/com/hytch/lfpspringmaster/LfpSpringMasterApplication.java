package com.hytch.lfpspringmaster;

import com.hytch.lfpspringmaster.sys.upload.StorageProperties;
import com.hytch.lfpspringmaster.sys.upload.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(
		exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class},
		scanBasePackages = {"com.hytch.lfpspringmaster"})
//如果不使用@ComponentScan指明对象扫描范围，默认指扫描当前启动类所在的包里的对象
@ServletComponentScan(basePackages = "com.hytch.lfpspringmaster")
@ComponentScan(basePackages = "com.hytch.lfpspringmaster")//组件扫描,若basePackages不配，// 则默认所在的包名
@EnableConfigurationProperties(StorageProperties.class)
public class LfpSpringMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(LfpSpringMasterApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
}
