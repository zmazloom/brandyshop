package com.brandyshop.document;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * config of Swagger
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.version.code}")
    private String versionCode;
    @Value("${swagger.title}")
    private String title;
    @Value("${swagger.description}")
    private String description;
    @Value("${swagger.url.config}")
    private String urlConfig;

    @Bean
    public Docket generalApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("general")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .paths(PathSelectors.regex("/api/v1/error.*").negate())
                .paths(PathSelectors.regex("/api/v1/actuator.*").negate())
                .build()
                .host(urlConfig)
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(securityContexts())
                .apiInfo(apiEndPointsInfo());

    }

    private ApiInfo apiEndPointsInfo() {

        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(versionCode)
                .build();

    }

    private List<SecurityContext> securityContexts() {

        return Collections.singletonList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .build()
        );

    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];

        authorizationScopes[0] = authorizationScope;

        return Collections.singletonList(new SecurityReference("Authorization", authorizationScopes));

    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }


    @Bean
    public UiConfiguration uiConfig() {

        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .docExpansion(DocExpansion.LIST)
                .filter(true)
                .build();

    }

    @Bean
    SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(false)
                .enableCsrfSupport(false)
                .build();
    }

    @ApiIgnore
    @RestController
    public static class Home {

        @GetMapping("/docs")
        public ModelAndView help(ModelMap model, HttpServletRequest request) {

            String sharp = request.getServletPath().substring(5);
            String redirect;

            if (StringUtils.isNotEmpty(sharp) && !sharp.equals("/")) {
                redirect = "redirect:" + "swagger-ui/index.html" + "#" + sharp;
            } else {
                redirect = "redirect:" + "swagger-ui/index.html";
            }

            return new ModelAndView(redirect, model);

        }

    }


}
