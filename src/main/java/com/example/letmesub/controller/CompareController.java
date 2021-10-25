package com.example.letmesub.controller;
import com.example.letmesub.dao.ISubscribeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class CompareController
{

    //구독db 접근
    @Autowired
    private ISubscribeDao subscribeDao;

    @GetMapping("/compare")
    public String compare(Model model, HttpServletRequest req){
        String subscribe1 = req.getParameter("subscribe1");
        String subscribe2 = req.getParameter("subscribe2");
        model.addAttribute("subs1", subscribe1);
        model.addAttribute("subs2", subscribe2);

        return "compare";
    }

   @GetMapping("/compare_selected")
    public String compare_selected(Model model, HttpServletRequest req){


        String likesubscribe = req.getParameter("like");
        String subscribe1 = req.getParameter("subscribe1");
        String subscribe2 = req.getParameter("subscribe2");

        subscribeDao.updateCount(likesubscribe);
       int like_u_num;
       int nonlike_u_num;

       if(req.getParameter("like").equals(req.getParameter("subscribe1"))){
           like_u_num = subscribeDao.viewCount(req.getParameter("subscribe1"));
           nonlike_u_num = subscribeDao.viewCount(req.getParameter("subscribe2"));
       }
       else {
           like_u_num = subscribeDao.viewCount(req.getParameter("subscribe2"));
           nonlike_u_num = subscribeDao.viewCount(req.getParameter("subscribe1"));
       }
       double like_u_num_f = (double)like_u_num;
       double nonlike_u_num_f = (double)nonlike_u_num;
       double preference = like_u_num_f / (like_u_num_f+nonlike_u_num_f) * 100.0;

       System.out.println(preference);
       int likepercentage = (int)preference;

       model.addAttribute("like_p", likepercentage);
       model.addAttribute("like", likesubscribe);
        model.addAttribute("subs1", subscribe1);
        model.addAttribute("subs2", subscribe2);
        return "compare_selected";
   }


}
