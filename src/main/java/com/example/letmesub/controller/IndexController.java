package com.example.letmesub.controller;
// 유민상

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController
{
    @GetMapping("/")
    public String index()
    {
        return "index";
    }
}
