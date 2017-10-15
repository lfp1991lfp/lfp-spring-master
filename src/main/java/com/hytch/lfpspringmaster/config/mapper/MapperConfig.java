package com.hytch.lfpspringmaster.config.mapper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis扫描类.
 */
@Configuration
@MapperScan("com.hytch.lfpspringmaster.model.mapper")
public class MapperConfig {
}
