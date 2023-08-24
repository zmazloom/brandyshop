package com.brandyshop;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;

/**
 * The main class of project
 *
 * @author Zahra Mazloom
 * @since 2023-08-24
 */

@OpenAPIDefinition(
        servers = {@Server(url = "${project.url.config}")},
        info = @Info(title = "${swagger.title}", version = "${server.version.code}", description = "${swagger.description}"))
@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
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
