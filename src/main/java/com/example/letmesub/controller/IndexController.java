package com.example.letmesub.controller;
// 유민상

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController
{
    @PostMapping("/index")
    public Map<String, String> SetUserInfo(@RequestBody  Map<String, String> GivenToken, HttpServletRequest request)
    {
        // 세션 가져오기
        HttpSession session = request.getSession();
        String sessionId = session.getAttribute("user").toString();
        System.out.println("session: "+sessionId);
        // Token 확인
        String LoginToken = GivenToken.get("login_token");
        System.out.println("Token:"+LoginToken);

        // 반환값
        Map<String, String> result = new HashMap<>();

        if (sessionId.equals(LoginToken))
        {
            // 쿠키정보와 세션정보가 일치
            result.put("result", "success");

        } else
        {
            result.put("result", "fail");
        }
        System.out.println(result.get("result"));
        return result;

    }

}
