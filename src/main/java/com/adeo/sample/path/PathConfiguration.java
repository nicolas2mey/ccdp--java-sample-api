package com.adeo.sample.path;

import com.adeo.sample.path.controllers.PathController;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Path configuration module definition.
 *
 * @author ccdccloudops@adeo.com
 */
@Configuration
@ComponentScan(basePackageClasses = PathController.class)
@NoArgsConstructor
public class PathConfiguration {
}
