package com.example.letmesub.controller;

import com.example.letmesub.dao.ISubscribeDao;
import com.example.letmesub.recommend.rec_query2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.letmesub.recommend.recAlgo3;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

// 조영우

@Controller
public class RecommendController
{
    //구독db 접근
    @Autowired
    private ISubscribeDao subscribeDao;
    //질문 가져옴
    rec_query2.ott category_ott = new rec_query2.ott();
    rec_query2.music category_music = new rec_query2.music();
    rec_query2.shopping category_shopping = new rec_query2.shopping();

    @GetMapping("/recommend")
    public String recommend(Model model, HttpServletRequest req)
    {
        //선택한 카테고리 가져옴 인덱스에서 쿼리스트링으로 정보 받을 예정
        String qcategory = req.getParameter("subscribe_category");
        if(qcategory.equals("all")) {
            return "recommend_c_select";
        }
        //나중에 합칠때 사용할 유저가 선택한 서비스 카테고리
        String user_selected_category= qcategory;
        System.out.println(user_selected_category);
        //카테고리에따라서 정해지는 쿼리 리스트
        List<String> q_list = new ArrayList<>();
        //카테고리와 연동하여 유저에게 보이는 질문 가져옴
        //지저분해서 나중에 변경 할수도있음
        if(user_selected_category.equals(category_ott.category)){
            q_list.add(category_ott.q1);
            q_list.add(category_ott.q2);
            q_list.add(category_ott.q3);
            q_list.add(category_ott.q4);
            q_list.add(category_ott.q5);
            q_list.add(category_ott.q6);
        }
        else if(user_selected_category.equals(category_music.category)){
            q_list.add(category_music.q1);
            q_list.add(category_music.q2);
            q_list.add(category_music.q3);
            q_list.add(category_music.q4);
            q_list.add(category_music.q5);
            q_list.add(category_music.q6);
        }
        else if(user_selected_category.equals(category_shopping.category)){
            q_list.add(category_shopping.q1);
            q_list.add(category_shopping.q2);
            q_list.add(category_shopping.q3);
            q_list.add(category_shopping.q4);
            q_list.add(category_shopping.q5);
            q_list.add(category_shopping.q6);
        }
        //카테고리정보 모델에 넣어서 보냄
        model.addAttribute("category", user_selected_category);

        //첫 접속시 count값 할당(값 할당해야 html오류안남->각 버튼의 하이퍼링크들, n번째 질문 1로 설정함)
        if(req.getParameter("count") == null && req.getParameter("weight") == null){
            int qcount = 1;
            String query = q_list.get(qcount-1);
            model.addAttribute("count",qcount);
            model.addAttribute("query", query);
            return "recommend";
        }


        //html에서 값 받아옴


        int qcount = Integer.parseInt(req.getParameter("count"));
        int uweight = Integer.parseInt(req.getParameter("weight"));
        //weight 세션 처리
        HttpSession session = req.getSession();
        //세션에 arraylist로 유저가 입력한 가중치 저장
        ArrayList<Integer> list = (ArrayList)session.getAttribute("weight");
        if(list==null){
            list = new ArrayList<Integer>();
            session.setAttribute("weight",list);
        }
        list.add(uweight);
        System.out.println(list);

        //뒤로가기를 했다가 다시하는 등 list개수와 질문개수 안맞을때 인덱스페이지로 이동함
        if(qcount != list.size()+1){
            //다시 이 페이지에서 접속해도 사용할수있게 list클리어 후 세션에 반영함
            list.clear();
            session.setAttribute("weight",list);
            return "index";
        }
        
        //모델에 query 전달, 구조상 사이즈 넘어가는 것은""로 처리했음
        if(qcount == q_list.size()+1) {
            model.addAttribute("query", "");
        }
        else {
            model.addAttribute("query", q_list.get(qcount - 1));
        }
        //모델에 count 전달
        model.addAttribute("count", qcount);

        //질문 6개 모두 완료했음
        if(qcount == 7){
            //최종적으로 골라진 구독 서비스의 이름
            String select_name = "";
            //최종적으로 골라진 구독 서비스의 경로
            String select_imgpath;
            System.out.println(subscribeDao.list(user_selected_category));

            //서비스별 점수를 비교함
            int score = 0;
            for(int i = 0; i < subscribeDao.list(user_selected_category).size(); i++){
                if(score == 0){
                    score = recAlgo3.rec_algo(subscribeDao.list(user_selected_category).get(i).getSubscribe_weight(), list);
                    select_name = subscribeDao.list(user_selected_category).get(i).getSubscribe_name();
                }
                else{
                    if(score < recAlgo3.rec_algo(subscribeDao.list(user_selected_category).get(i).getSubscribe_weight(), list))
                    {
                        score = recAlgo3.rec_algo(subscribeDao.list(user_selected_category).get(i).getSubscribe_weight(), list);
                        select_name = subscribeDao.list(user_selected_category).get(i).getSubscribe_name();
                    }
                }
            }
            model.addAttribute("name", select_name);

            //다시 이 페이지에서 접속해도 사용할수있게 list클리어 후 세션에 반영함
            list.clear();
            session.setAttribute("weight",list);
            return "recommend_choosing";
        }
        return "recommend";
    }

    @GetMapping("/recommend_choosing")
    public String recommend_choosing(Model model, HttpServletRequest req)
    {
        return "recommend_choosing";
    }

    @GetMapping("/recommend_selected")
    public String recommend_selected(Model model, HttpServletRequest req)
    {
        String select_name = req.getParameter("subscribe_name");
        model.addAttribute("name", select_name);
        return "recommend_selected";
    }
}
