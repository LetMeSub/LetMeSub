package com.example.letmesub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
// 조영우

@Controller
public class RecommendController
{

    @GetMapping("/recommend")
    public String recommand()
    {
        return "recommend";
    }
}
