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
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
@EnableSwagger2
public class SwaggerConfig {
    public static final String AUTHORIZATION_HEADER = "Authorization";

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

    @Bean
    public Docket ecommerceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(Collections.singletonList(securityContextList()))
                .securitySchemes(Collections.singletonList(new ApiKey("JWT", AUTHORIZATION_HEADER, "header")))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.laptech.restapi.controller"))
                .paths(PathSelectors.regex("/api/v1.*"))
                .build()
                .directModelSubstitute(LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(LocalDateTime.class, java.util.Date.class);
    }

    private SecurityContext securityContextList() {
        return SecurityContext
                .builder()
                .securityReferences(securityReferenceList())
                .build();
    }

    private List<SecurityReference> securityReferenceList() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
    }


}

