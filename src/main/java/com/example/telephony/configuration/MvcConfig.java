package com.example.telephony.configuration;

import com.example.telephony.common.PropertiesHelper;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    private final Environment environment;

    public MvcConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadFiles = Paths.get(PropertiesHelper.getProperty(environment, "file.storage.path"));
        String uploadFilesPattern = PropertiesHelper.getProperty(environment, "file.storage.pattern");
        registry
                .addResourceHandler(uploadFilesPattern)
                .addResourceLocations(uploadFiles.toUri().toString());

        Path generatedFiles = Paths.get(PropertiesHelper.getProperty(environment, "file.generated.path"));
        String generatedFilesPattern = PropertiesHelper.getProperty(environment, "file.generated.pattern");
        registry
                .addResourceHandler(generatedFilesPattern)
                .addResourceLocations(generatedFiles.toUri().toString());

        registry
                .addResourceHandler("/docApi/swagger-ui.html**")
                .addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");

        registry
                .addResourceHandler("/docApi/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/docApi/v2/api-docs", "/v2/api-docs");
        registry.addRedirectViewController("/docApi/swagger-resources/configuration/ui",
                "/swagger-resources/configuration/ui");
        registry.addRedirectViewController("/docApi/swagger-resources/configuration/security",
                "/swagger-resources/configuration/security");
        registry.addRedirectViewController("/docApi/swagger-resources", "/swagger-resources");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(PropertiesHelper.getProperty(environment, "server.input.url"))
                .allowedMethods("*");
    }
}
