package com.example.text.to.speech.service.config;

import com.example.text.to.speech.service.common.PropertiesHelper;
import com.example.text.to.speech.service.enums.CustomApplicationProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    private final Environment environment;

    public WebMvcConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path generatedFiles = Paths.get(PropertiesHelper.getApplicationProperty(CustomApplicationProperty.TTS_RESULT_FILE, environment));
        createDirectoryIfNotExist(generatedFiles);
        String resourceLocation = generatedFiles.toUri().toString();
        String generatedFilesPattern = PropertiesHelper.getApplicationProperty(CustomApplicationProperty.TTS_RESULT_PATTERN, environment);
        registry
                .addResourceHandler(generatedFilesPattern)
                .addResourceLocations(resourceLocation);

        registry
                .addResourceHandler("/api/swagger-ui.html**")
                .addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");

        registry
                .addResourceHandler("/api/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private static void createDirectoryIfNotExist(Path path) {
        path.toAbsolutePath().toFile().mkdirs();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/api/v2/api-docs", "/v2/api-docs");
        registry.addRedirectViewController("/api/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
        registry.addRedirectViewController("/api/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
        registry.addRedirectViewController("/api/swagger-resources", "/swagger-resources");
    }
}
