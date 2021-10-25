package com.example.letmesub.controller;

import com.example.letmesub.dao.ISubscribeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.http.HttpServletRequest;
// 안주현

@Controller
public class DetailController
{
    //구독db 접근
    @Autowired
    private ISubscribeDao subscribeDao;

    @GetMapping("/detail")
    public String detail(Model model, HttpServletRequest req)
    {

        String subscribe = req.getParameter("subscribe_name");
        System.out.println(subscribe);
        int rate = subscribeDao.viewAll(subscribe).getSubscribe_rate();
        String desc = subscribeDao.viewAll(subscribe).getSubscribe_describe();

        model.addAttribute("subscribe", subscribe);
        model.addAttribute("rate", rate);
        model.addAttribute("desc", desc);
        return "detail";
    }
}
