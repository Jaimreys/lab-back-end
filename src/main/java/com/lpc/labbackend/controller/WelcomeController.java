package com.lpc.labbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping("/hello_world")
    public String hello() {
        return "Hello World";
    }
}
