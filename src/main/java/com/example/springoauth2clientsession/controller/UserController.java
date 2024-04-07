package com.example.springoauth2clientsession.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/user")
    public String UserPage(){

        return "User Controller";
    }
}
