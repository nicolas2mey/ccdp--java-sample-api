package com.adeo.sample.core;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Core configuration Spring module definition.
 *
 * @author ccdccloudops@adeo.com
 */
@Configuration
@ComponentScan(basePackageClasses = RestControllerAdvice.class)
@NoArgsConstructor
public class CoreConfiguration {
}
