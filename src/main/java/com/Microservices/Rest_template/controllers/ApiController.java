package com.Microservices.Rest_template.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Microservices.Rest_template.entity.User;
import com.Microservices.Rest_template.serivces.ApiService;

@RestController
@RequestMapping("/rest-template")
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/")
    public String getSessionId() {
        apiService.getSessionId();
        String firstPartCode = apiService.getPartCode(new User((long) 3, "James", "Brown", (byte) 35));
        String nextPartCode = apiService.getPartCode(new User((long) 3, "Thomas", "Shelby", (byte) 35));
        String endPartCode = apiService.deleteUser(3L);
        return firstPartCode + nextPartCode + endPartCode;
    }

}
