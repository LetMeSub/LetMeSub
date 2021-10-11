package com.example.letmesub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.letmesub.recommend.recAlgo;

import javax.servlet.http.HttpServletRequest;
// 조영우

@Controller
public class RecommendController
{

    @GetMapping("/recommend")
    public String recommend(Model model, HttpServletRequest req)
    {
        if(req.getQueryString() == null){
            int qcount = 1;
            model.addAttribute("count", qcount);
            return "recommend";
        }
        int qcount = Integer.parseInt(req.getParameter("count"));
        model.addAttribute("count", qcount);
        if(qcount == 7){
            return "recommend_selected";
        }
        return "recommend";
    }
}

