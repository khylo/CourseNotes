package com.khylo.security.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthController {
    

    @GetMapping("/user")
    public String user(){
        return "<h1> User Area</h1><p>Welcome to the User area. You are an authorized user</p>";
    }

    @GetMapping("/admin")
    public String admin(){
        return "<h1> Admin Area</h1><p>Welcome to the Admin area. You are an Admin user</p>";
    }
}
