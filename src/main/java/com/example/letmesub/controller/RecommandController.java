package com.example.letmesub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
// 조영우
//ddd

@Controller
public class RecommandController
{

    @GetMapping("/recommssssand")
    public String recommand()
    {
        return "recommand";
    }
}
