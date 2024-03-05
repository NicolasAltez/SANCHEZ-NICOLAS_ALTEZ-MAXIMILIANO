package com.backend.integrador.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @GetMapping("/ping")
    public String getPing(){
        return "api is working!";
    }
}
