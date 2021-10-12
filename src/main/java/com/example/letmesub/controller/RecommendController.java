package com.example.letmesub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.letmesub.recommend.recAlgo3;

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
        
        //질문 6개 모두 완료했음
        if(qcount == 7){
            
            //db에서 뽑아올 서비스 가중치
            int a = 222222;
            //최종 점수 계산
            int weight = recAlgo3.rec_algo(a, list);
            System.out.println(weight);
            return "recommend_selected";
        }
        return "recommend";
    }
}
