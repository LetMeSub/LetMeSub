package com.example.letmesub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.letmesub.recommend.recAlgo;
// 조영우

@Controller
public class RecommendController
{

    @GetMapping("/recommend")
    public String recommend(Model model)
    {
        recAlgo.service service1 = new recAlgo.service("넷플릭스", 1,2,
                3,4,5,6);
        recAlgo.service service2 = new recAlgo.service("디즈니", 6,5,
                1,2,3,4);
        recAlgo.service service3 = new recAlgo.service("왓챠", 2,1,
                6,5,4,3);
        recAlgo.service service4 = new recAlgo.service("훌루", 5,4,
                1,2,3,6);
        recAlgo.service service5 = new recAlgo.service("아마존프라임", 3,2,
                5,6,4,1);

        recAlgo.set_user_weight value = new recAlgo.set_user_weight(1,3,7,6,8,1);//사용자 선호도

        service1.set_score(value.user_factor1, value.user_factor2, value.user_factor3,
                value.user_factor4, value.user_factor5, value.user_factor6);

        service2.set_score(value.user_factor1, value.user_factor2, value.user_factor3,
                value.user_factor4, value.user_factor5, value.user_factor6);

        service3.set_score(value.user_factor1, value.user_factor2, value.user_factor3,
                value.user_factor4, value.user_factor5, value.user_factor6);

        service4.set_score(value.user_factor1, value.user_factor2, value.user_factor3,
                value.user_factor4, value.user_factor5, value.user_factor6);

        service5.set_score(value.user_factor1, value.user_factor2, value.user_factor3,
                value.user_factor4, value.user_factor5, value.user_factor6);

        recAlgo.sort_algo select_service = new recAlgo.sort_algo();
        select_service.sort(
                service1.score, service2.score, service3.score,
                service4.score, service5.score);
        if (service1.score == select_service.winner){
            System.out.printf("내가 선택한 서비스는 " + service1.name);
        }
        if (service2.score == select_service.winner){
            System.out.printf("내가 선택한 서비스는 " + service2.name);
        }
        if (service3.score == select_service.winner){
            System.out.printf("내가 선택한 서비스는 " + service3.name);
        }
        if (service4.score == select_service.winner){
            System.out.printf("내가 선택한 서비스는 " + service4.name);
        }
        if (service5.score == select_service.winner){
            System.out.printf("내가 선택한 서비스는 " + service5.name);
        }
        int count = 1;
        if(count == 1) {
            model.addAttribute("userquery", "첫번째 질문");
            count++;
        }
        else if(count == 2){
            model.addAttribute("userquery", "두번째 질문");
        }
        return "recommend";
    }
}

