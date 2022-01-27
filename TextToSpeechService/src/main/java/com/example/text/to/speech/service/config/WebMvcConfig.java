package com.example.text.to.speech.service.config;

import com.example.text.to.speech.service.common.PropertiesHelper;
import com.example.text.to.speech.service.enums.CustomApplicationProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
        String generatedFilesPattern = PropertiesHelper.getApplicationProperty(CustomApplicationProperty.TTS_RESULT_PATTERN, environment);
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
}
