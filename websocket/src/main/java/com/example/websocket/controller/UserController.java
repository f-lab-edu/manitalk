package com.example.websocket.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @Value("${auth.url}")
    private String authUrl;

    @Value("${logout.url}")
    private String logoutUrl;

    @GetMapping("/")
    public String index() {
        return "redirect:login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("authUrl", authUrl);
        return "login";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("logoutUrl", logoutUrl);
        return "home";
    }
}
