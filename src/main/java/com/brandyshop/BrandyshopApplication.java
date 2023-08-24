package com.brandyshop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The main class of project
 *
 * @author Zahra Mazloom
 * @since 2023-08-24
 */

@SpringBootApplication
@EnableConfigurationProperties
@EntityScan(basePackages = {"com.brandyshop.domain.data"})
@EnableJpaRepositories(basePackages = {"com.brandyshop"})
public class BrandyshopApplication {

    private static String sessionId;

    public static void main(String[] args) {

        SpringApplication.run(BrandyshopApplication.class, args);

    }

    @Value("${server.servlet.session.cookie.name}")
    public void setSessionId(String sessionId) {
        BrandyshopApplication.sessionId = sessionId;
    }

    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return servletContext -> servletContext.getSessionCookieConfig().setName(sessionId);
    }

}
