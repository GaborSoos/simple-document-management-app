package com.masterwork.simpledocumentmanagmentapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

  public static final String AUTHORIZATION_HEADER = "Authorization";

  private ApiKey apiKey() {
    return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
  }

  private ApiInfo apiInfo() {
    return new ApiInfo(
        "Simple Document Management Application REST API",
        "Simple Document Management Application REST API Documentation",
        "1",
        "",
        new Contact("Gábor Soós", "https://github.com/GaborSoos", ""),
        "License of API",
        "https://github.com/GaborSoos",
        Collections.emptyList()
    );
  }

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .securityContexts(Arrays.asList(securityContext()))
        .securitySchemes(Arrays.asList(apiKey()))
        .select()
        .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        .paths(PathSelectors.any())
        .build();
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder().securityReferences(defaultAuth()).build();
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverithing");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
  }




}
