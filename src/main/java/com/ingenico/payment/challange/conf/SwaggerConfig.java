package com.ingenico.payment.challange.conf;

import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

import static springfox.documentation.builders.PathSelectors.regex;

//@Configuration
@EnableSwagger2
@Profile("!test")
public class SwaggerConfig {

    @Autowired
    private ApplicationInfo application;

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("public-api").apiInfo(apiInfo()).select()
                .paths(postPaths()).build();
    }

    private Predicate<String> postPaths() {
        return regex("/api.*");
    }

    @SuppressWarnings("deprecation")
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(application.getApplicationName())
                .description(application.getApplicationDescription())
                .contact(Arrays.toString(application.getReviewers())).version(application.getApplicationVersion())
                .build();
    }

}
