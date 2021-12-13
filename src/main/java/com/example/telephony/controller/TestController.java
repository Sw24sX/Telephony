package com.example.telephony.controller;

import com.example.telephony.common.GlobalMapping;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(GlobalMapping.API + "test")
public class TestController {
    @GetMapping
    public String mustache() {
        String text = "One, two, {{three}}. Three sir!";
        Template tmpl = Mustache.compiler().compile(text);
        Map<String, String> data = new HashMap<>();
        data.put("three", "five");
        return tmpl.execute(data);
    }
}
