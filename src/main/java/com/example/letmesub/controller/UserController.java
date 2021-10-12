package com.example.letmesub.controller;

import com.example.letmesub.dto.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
//유민상

@RestController
public class UserController
{



    /*
     회원가입 기능
     날짜:20211009
     register.html 에서 아이디 비밀번호 이메일 값을 json 으로 받아옴
     받아온 json값을 User 객체로 매핑
     user 객체를 db에 암호화해서 저장
     저장 성공시 String 'success' 반환
     */
    @PostMapping("/api/register")
    public Map<String,String> user_register(@RequestBody User user)
    {
        System.out.println(user.toString());
        Map<String,String> result = new HashMap<>();

        try
        {
         // DB에 User 정보 삽입


            result.put("result", "success");

        } catch (Exception e)
        {
            e.printStackTrace();
        }




        return result;
    }
}
