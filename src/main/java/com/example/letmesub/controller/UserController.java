package com.example.letmesub.controller;

import com.example.letmesub.dao.UserDao;
import com.example.letmesub.dto.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//유민상

@RestController
public class UserController
{
    @Autowired
    private UserDao userDao;


    /*
     회원가입 기능
     날짜:20211009
     register.html 에서 아이디 비밀번호 이메일 값을 json 으로 받아옴
     받아온 json값을 User 객체로 매핑
     user 객체를 db에 암호화해서 저장
     저장 성공시 String 'success' 반환
     */
    @PostMapping("/api/register")
    public Map<String, String> user_register(@RequestBody User user)
    {
        System.out.println(user.toString());
        Map<String, String> result = new HashMap<>();
        // password 암호화 과정 필요
        try
        {
            // DB에 User 정보 삽입
            userDao.insertUser(user);
            result.put("result", "success");

        } catch (Exception e)
        {
            e.printStackTrace();
        }


        return result;
    }


    @RequestMapping("/api/user")
    public List<User> user()
    {
        System.out.println("db con");
        return userDao.selectUsers();

    }

    @PostMapping("api/login")
    public Map<String, String> user_login(@RequestBody Map<String, String> GivenId, HttpServletRequest request)
    {
        Map<String, String> result = new HashMap<>();

        // 로그인
        try
        {
            // DB 에서 로그인 암호와 PW 가져와 비교
            User FindedUser = userDao.findUserById(GivenId.get("user_id"));
            System.out.println(FindedUser);

            // 가져온 객체가 null인지 값이 있는지 확인
            if (FindedUser.getUser_id() != null)
            {
                // null 이 아닌경우 pw 검사
                if (GivenId.get("user_pw").equals(FindedUser.getUser_pw()))
                {
                    // pw 일치 회원 로그인 가능
                    System.out.println("로그인 연결성공");

                    //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
                    HttpSession session = request.getSession();

                    session.setAttribute("user",FindedUser.getUser_id());
                    System.out.println("session-attribute: "+session.getAttribute("user"));
                    System.out.println(session.getCreationTime());
                    result.put("result", "success");
                }
                else
                {
                    // pw 불 일치 회원 로그인 불가
                    System.out.println("로그인 연결실패");
                    result.put("result", "fail");
                }

            } else
            {
                // null 인 경우 아이디가 존재 x 로그인 실패 출력
                result.put("result", "fail");
            }


        } catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println(result.get("result"));
        return result;
    }


}
