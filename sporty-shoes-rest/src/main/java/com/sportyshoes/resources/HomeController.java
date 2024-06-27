package com.sportyshoes.resources;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/admin/login")
    public String adminLogin() {
        return "admin-login";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin-dashboard";
    }
    
    @GetMapping("/admin/change-password")
    public String adminChangePassword() {
        return "admin-change-password";
    }
}
