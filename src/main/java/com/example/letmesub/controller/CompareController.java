package com.example.letmesub.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CompareController
{
    @GetMapping("/compare")
    public String compare(){

        return "compare";
    }
}
