package com.example.letmesub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.letmesub.recommend.recAlgo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
// 조영우

@Controller
public class RecommendController
{

    @GetMapping("/recommend")
    public String recommend(Model model, HttpServletRequest req)
    {
        //첫 접속시 count값 할당
        if(req.getQueryString() == null){
            int qcount = 1;
            model.addAttribute("count",qcount);
            return "recommend";
        }
        
        //html에서 값 받아옴
        int qcount = Integer.parseInt(req.getParameter("count"));
        int uweight = Integer.parseInt(req.getParameter("weight"));

        //weight 세션 처리
        HttpSession session = req.getSession();
        ArrayList<Integer> list = (ArrayList)session.getAttribute("weight");
        if(list==null){
            list = new ArrayList<Integer>();
            session.setAttribute("weight",list);
        }
        list.add(uweight);
        System.out.println(list);
        
        //모델에 count 전달
        model.addAttribute("count", qcount);

        if(qcount == 7){
            return "recommend_selected";
        }
        return "recommend";
    }
}
