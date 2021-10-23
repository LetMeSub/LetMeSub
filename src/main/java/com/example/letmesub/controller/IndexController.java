package com.example.letmesub.controller;
// 유민상

import com.example.letmesub.dao.IndexDao;
import com.example.letmesub.dao.UserDao;
import com.example.letmesub.dto.SubscribeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private IndexDao indexDao;

    // 인덱스에서 카테고리 선택시 해당 카테고리에 해당하는 데이터를 가져와 뿌려주는 함수
    @GetMapping("/api/index")
    public Map<String, String> SetIndexContent(String category)
    {

        // DB 에서 해당 카테고리 가져오기
        List<SubscribeDto> SelectedList = indexDao.IndexList(category);

        for (SubscribeDto i : SelectedList)
        {
            System.out.println(i.getSubscribe_name());
        }

        // 반환값
        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        return result;
    }



}
