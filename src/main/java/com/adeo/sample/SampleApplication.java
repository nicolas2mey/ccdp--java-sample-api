package com.adeo.sample;

import com.adeo.sample.core.CoreConfiguration;
import com.adeo.sample.number.NumberConfiguration;
import com.adeo.sample.user.UserConfiguration;
import com.adeo.sample.warmup.WarmupConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Main entry point.
 *
 * @author ccdpcloudops@adeo.com
 */
@SpringBootApplication
@Import({
    CoreConfiguration.class,
    UserConfiguration.class,
    NumberConfiguration.class,
    WarmupConfiguration.class
})
public class SampleApplication {

    static {
        System.setProperty("reactor.netty.http.server.accessLogEnabled", "true");
    }
    
    

    /**
     * Application entry point.
     *
     * @param args
     *     application parameters.
     */
    public static void main(final String... args) {
        System.put.println("this will not pass !") ; 
        SpringApplication.run(SampleApplication.class, args);
    }
}
