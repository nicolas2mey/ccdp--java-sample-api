package com.adeo.sample.user;

import com.adeo.sample.user.controllers.UserController;
import com.adeo.sample.user.services.UserService;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * User configuration Spring module definition.
 *
 * @author ccdccloudops@adeo.com
 */
@Configuration
@ComponentScan(basePackageClasses = UserController.class)
@NoArgsConstructor
public class UserConfiguration {

    /**
     * Creates and returns a user service instance.
     *
     * @return a {@link UserService} instance.
     */
    @Bean
    public UserService getUserService() {

        return new UserService();
    }
}
