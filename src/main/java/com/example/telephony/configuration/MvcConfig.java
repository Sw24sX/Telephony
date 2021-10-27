package com.example.telephony.configuration;

import com.example.telephony.common.Properties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
        Path path = Paths.get(Properties.getProperty(environment, "file.storage.path"));
        String pattern = Properties.getProperty(environment, "file.storage.pattern");
        registry
                .addResourceHandler(pattern)
                .addResourceLocations(path.toUri().toString());
    }
}
