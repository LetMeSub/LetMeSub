package com.example.letmesub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController
{
    @GetMapping("/recommand")
    public String recommand()
    {
        return "recommand";
    }

    @GetMapping("/")
    public String index()
    {
        return "index";
    }

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping("/register")
    public String register()
    {
        return "register";
    }

    @GetMapping("/detail")
    public String detail()
    {
        return "detail";
    }
}
