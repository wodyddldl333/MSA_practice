package com.example.ms1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/ms1/first")
    public String mainP() {

        System.out.println("ms1");
        return "main";
    }

    @GetMapping("/ms1/login")
    public void loginTest() {

        System.out.println("login attempt");
    }

    @GetMapping("/ms1/join")
    public void joinTest() {

        System.out.println("join attempt");
    }
}
