package com.example.telephony;

import com.example.telephony.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TelephonyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelephonyApplication.class, args);
    }
}
