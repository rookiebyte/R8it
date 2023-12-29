package com.rit.starterboot.servlet.configuration.swagger;

import com.rit.robusta.util.Strings;
import com.rit.starterboot.servlet.configuration.swagger.properties.BuildProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.RouterOperationCustomizer;
import org.springdoc.core.customizers.ServerBaseUrlCustomizer;
import org.springdoc.core.fn.RouterOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

@Configuration
@EnableConfigurationProperties({BuildProperties.class})
public class SwaggerConfiguration implements RouterOperationCustomizer, ServerBaseUrlCustomizer {

    public static final String BEARER_AUTH_SECURITY_NAME = "bearer_auth";
    private final String basePath;

    public SwaggerConfiguration(@Value("${server.servlet.contextPath}") String basePath) {
        this.basePath = basePath;
    }

    @Bean
    public OpenAPI openAPI(@Value("${spring.application.name}") String name, BuildProperties build) {
        var openApi = new OpenAPI();
        info(openApi, name, build);
        openApiSecurity(openApi);
        return openApi;
    }

    private void info(OpenAPI openAPI, String name, BuildProperties build) {
        if (Strings.isNotEmpty(name)) {
            var info = new Info();
            info.setTitle(name);
            info.setVersion(build.version());
            info.description(build.description());
            openAPI.info(info);
        }
    }

    private void openApiSecurity(OpenAPI openApi) {
        var securityScheme = new SecurityScheme()
                .name(BEARER_AUTH_SECURITY_NAME)
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
        openApi.addSecurityItem(new SecurityRequirement().addList(BEARER_AUTH_SECURITY_NAME))
               .components(new Components().addSecuritySchemes(BEARER_AUTH_SECURITY_NAME, securityScheme));
    }

    @Override
    public RouterOperation customize(RouterOperation routerOperation, HandlerMethod handlerMethod) {
        routerOperation.setPath(basePath + routerOperation.getPath());
        return routerOperation;
    }

    @Override
    public String customize(String serverBaseUrl) {
        return null;
    }
}
