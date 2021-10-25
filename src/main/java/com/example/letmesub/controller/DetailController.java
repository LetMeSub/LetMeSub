package com.example.letmesub.controller;

import com.example.letmesub.dao.ICommentDao;
import com.example.letmesub.dao.ISubscribeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
// 안주현

@Controller
public class DetailController
{
    //구독db 접근
    @Autowired
    private ISubscribeDao subscribeDao;

    @Autowired
    private ICommentDao commentdao;

    @GetMapping("/detail")
    public String detail(Model model, HttpServletRequest req)
    {

        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = req.getSession();
        String user_id;
        String style;
        if(session.getAttribute("user") != null)
        {
            user_id = session.getAttribute("user").toString();
            style = "block";
        }
        else
        {
            user_id = null;
            style = "none";
        }

        System.out.println(user_id);


        String subscribe = req.getParameter("subscribe_name");
        System.out.println(subscribe);
        int rate = subscribeDao.viewAll(subscribe).getSubscribe_rate();
        String desc = subscribeDao.viewAll(subscribe).getSubscribe_describe();


        System.out.println(commentdao.commentList(subscribe));

        model.addAttribute("list", commentdao.commentList(subscribe));
        model.addAttribute("subscribe", subscribe);
        model.addAttribute("rate", rate);
        model.addAttribute("desc", desc);
        model.addAttribute("style", style);
        return "detail";
    }

    @PostMapping("/detail")
    public String detail2(Model model, HttpServletRequest req)
    {

        //로그인된 상태에서만 넘어오기때문에 세션값 항상있음
        HttpSession session = req.getSession();
        //세션정보에서 받아와서 넣어줘야 함
        String user_id = session.getAttribute("user").toString();

        String subscribe = req.getParameter("subscribe");
        String context = req.getParameter("context");
        System.out.println(subscribe);
        System.out.println(context);
        System.out.println(user_id);

        System.out.println(commentdao.insertComment(context, user_id, subscribe));

        int rate = subscribeDao.viewAll(subscribe).getSubscribe_rate();
        String desc = subscribeDao.viewAll(subscribe).getSubscribe_describe();


        System.out.println(commentdao.commentList(subscribe));

        model.addAttribute("list", commentdao.commentList(subscribe));
        model.addAttribute("subscribe", subscribe);
        model.addAttribute("rate", rate);
        model.addAttribute("desc", desc);
        return "detail";
    }
}
