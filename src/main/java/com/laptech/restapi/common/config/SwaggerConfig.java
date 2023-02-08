package com.laptech.restapi.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * <a href="https://www.youtube.com/watch?v=kE_iN9eFYyw">Config Authorization in Swagger</a>
 *
 * @author Nhat Phi
 * @version 2.0.1
 * @since 2022-12-26
 */
@Configuration
public class SwaggerConfig {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private ApiKey apiKeys() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private List<SecurityContext> securityContextList() {
        return Collections.singletonList(SecurityContext
                .builder()
                .securityReferences(securityReferenceList())
                .build());
    }

    private List<SecurityReference> securityReferenceList() {
        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
        return Collections.singletonList(
                new SecurityReference("JWT", new AuthorizationScope[]{scope})
        );
    }

    @Bean
    public Docket ecommerceApi() {
        return new Docket(DocumentationType.OAS_30)
                .securityContexts(securityContextList())
                .securitySchemes(Collections.singletonList(apiKeys()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.laptech.restapi.controller"))
                .paths(PathSelectors.regex("/api/v1.*"))
                .build()
                .directModelSubstitute(LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Laptech APIs")
                .description("This api was created by spring boot and rest controller!")
                .version("2.0.1")
//                .license("Apache License Version 2.0")
//                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .contact(new Contact("Nhat Phi", null, "sunflynf@gmail.com"))
                .build();
    }
}

