package com.cy.bookstore.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

/**
 * Swagger配置类.
 *
 * @author Legion
 * @since 2022-04-14 19:08:10
 */
@Configuration
@EnableWebMvc
public class SwaggerConfig {

    @Value("true")
    private boolean swagEnable;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                // 配置是否启用Swagger
                .enable(swagEnable)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths((String uri) -> uri.matches("/api/.*"))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("网上书城")
                .description("接口文档")
                .contact(new Contact("Taihi", "future-math.dayandata.cn", "1342275724@qq.com")) // 这里要怎么理解？
                .version("1.0")
                .build();
    }

    private List<SecurityScheme> securitySchemes() {  // 这个类有什么用？
        return Collections.singletonList(new ApiKey("Authorization", "Authorization", "header"));
    }

    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .operationSelector(operationContext -> operationContext.requestMappingPattern().matches("^(?!auth).*$"))
                .build());
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = new AuthorizationScope("global", "accessEverything");
        return Collections.singletonList(new SecurityReference("Authorization", authorizationScopes));
    }

}
