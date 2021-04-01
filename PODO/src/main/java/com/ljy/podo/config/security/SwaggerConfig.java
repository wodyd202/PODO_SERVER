package com.ljy.podo.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private ApiInfo apiInfo(String title) {
        return new ApiInfoBuilder()
                .title(title)
                .description("API DOCS")
                .build();
    }

    @Bean
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Podo")
                .apiInfo(this.apiInfo("Podo API"))
                .select()
                .apis(Predicates.or(RequestHandlerSelectors
                        .basePackage("com.ljy.podo.user.api"),
                        RequestHandlerSelectors
    					.basePackage("com.ljy.podo.portfolio.api")))
                .paths(PathSelectors.ant("/api/v1/**"))
                .build();
    }
}