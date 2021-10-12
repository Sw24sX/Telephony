package com.example.telephony.service;

import org.springframework.beans.factory.annotation.Value;

public class CallerService {
    @Value("${asterisk.username}")
    private String username;

    @Value("${asterisk.password}")
    private String password;

    @Value("${asterisk.url}")
    private String url;
}
