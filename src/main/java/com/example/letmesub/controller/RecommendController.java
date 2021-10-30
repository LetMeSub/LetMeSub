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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

// 조영우

@Controller
public class RecommendController
{
    //구독db 접근
    @Autowired
    private ISubscribeDao subscribeDao;
    //공통질문 가져옴
    rec_query2.common common = new rec_query2.common();
    
    //질문 가져옴
    rec_query2.ott category_ott = new rec_query2.ott();
    rec_query2.music category_music = new rec_query2.music();
    rec_query2.shopping category_shopping = new rec_query2.shopping();

    @GetMapping("/recommend")
    public String recommend(Model model, HttpServletRequest req)
    {
        //선택한 카테고리 가져옴 인덱스에서 쿼리스트링으로 정보 받을 예정
        String qcategory = req.getParameter("subscribe_category");
        //세션 객체선언
        HttpSession session = req.getSession();
        if(qcategory.equals("all")){
            return "recommend_c_select";
        }
        //나중에 합칠때 사용할 유저가 선택한 서비스 카테고리
        String user_selected_category= qcategory;
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
        //세션에 arraylist로 유저가 입력한 가중치 저장
        ArrayList<Integer> list = (ArrayList)session.getAttribute("weight");
        System.out.println("dd"+(ArrayList)session.getAttribute("weight"));
        ArrayList<Integer> common_list = (ArrayList)session.getAttribute("common_weight");
        System.out.println(user_selected_category);
        System.out.println("유저가 입력한 공통질문의 대답은 "+common_list);
        System.out.println("유저가 입력한 카테고리별 질문의 대답은 "+list);
        if(list==null){
            list = new ArrayList<Integer>();
            session.setAttribute("weight",list);
        }
        list.add(uweight);
        System.out.println(user_selected_category);
        System.out.println("유저가 입력한 공통질문의 대답은 "+common_list);
        System.out.println("유저가 입력한 카테고리별 질문의 대답은 "+list);

        //뒤로가기를 했다가 다시하는 등 list개수와 질문개수 안맞을때 인덱스페이지로 이동함
        if(qcount != list.size()+1){
            //다시 이 페이지에서 접속해도 사용할수있게 list클리어 후 세션에 반영함
            list.clear();
            session.setAttribute("weight",list);
            session.setAttribute("common_weight",list);
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
            ArrayList<Integer> weight_list = new ArrayList<>(Arrays.asList(0,0,0,0,0,0));
            //공통질문 가중치 적용함
            if(user_selected_category.equals("ott")){
                //첫번째 공통질문에서 답이 1일경우 카테고리별질문 5,1에 가중치 더함
                if(common_list.get(0)==1)
                {weight_list.set(4, weight_list.get(4)+2);
                    weight_list.set(0, weight_list.get(0)+2);}
                //첫번째 공통질문에서 답이 0일경우 카테고리별질문 3,6에 가중치 더함
                else
                {weight_list.set(2, weight_list.get(2)+2);
                    weight_list.set(5, weight_list.get(5)+2);}
                //두번째 공통질문에서 답이 1일경우 카테고리별질문 2,6에 가중치 더함
                if(common_list.get(1)==1)
                {weight_list.set(1, weight_list.get(1)+2);
                    weight_list.set(5, weight_list.get(5)+2);}
                //이하 동일
                else
                {weight_list.set(0, weight_list.get(0)+2);
                    weight_list.set(2, weight_list.get(2)+2);}
                if(common_list.get(2)==1)
                {weight_list.set(1, weight_list.get(1)+2);
                    weight_list.set(4, weight_list.get(4)+2);}
                else
                {weight_list.set(0, weight_list.get(0)+2);
                    weight_list.set(3, weight_list.get(3)+2);}
                if(common_list.get(3)==1)
                {weight_list.set(1, weight_list.get(1)+2);
                    weight_list.set(3, weight_list.get(3)+2);}
                else
                {weight_list.set(0, weight_list.get(0)+2);
                    weight_list.set(4, weight_list.get(4)+2);}
                if(common_list.get(4)==1)
                {weight_list.set(1, weight_list.get(1)+2);
                    weight_list.set(5, weight_list.get(5)+2);}
                else
                {weight_list.set(0, weight_list.get(0)+2);
                    weight_list.set(3, weight_list.get(3)+2);}
                if(common_list.get(5)==1)
                {weight_list.set(1, weight_list.get(1)+2);
                    weight_list.set(2, weight_list.get(2)+2);}
                else
                {weight_list.set(4, weight_list.get(4)+2);
                    weight_list.set(5, weight_list.get(5)+2);}
            }
            else if(user_selected_category.equals("music")){
                //첫번째 공통질문에서 답이 1일경우 카테고리별질문 5,1에 가중치 더함
                if(common_list.get(0)==1)
                {weight_list.set(0, weight_list.get(0)+2);
                    weight_list.set(5, weight_list.get(5)+2);}
                //첫번째 공통질문에서 답이 0일경우 카테고리별질문 3,6에 가중치 더함
                else
                {weight_list.set(2, weight_list.get(2)+2);
                    weight_list.set(3, weight_list.get(3)+2);}
                //두번째 공통질문에서 답이 1일경우 카테고리별질문 2,6에 가중치 더함
                if(common_list.get(1)==1)
                {weight_list.set(3, weight_list.get(3)+2);
                    weight_list.set(5, weight_list.get(5)+2);}
                //이하 동일
                else
                {weight_list.set(1, weight_list.get(1)+2);
                    weight_list.set(4, weight_list.get(4)+2);}
                if(common_list.get(2)==1)
                {weight_list.set(2, weight_list.get(2)+2);
                    weight_list.set(3, weight_list.get(3)+2);}
                else
                {weight_list.set(4, weight_list.get(4)+2);
                    weight_list.set(5, weight_list.get(5)+2);}
                if(common_list.get(3)==1)
                {weight_list.set(1, weight_list.get(1)+2);
                    weight_list.set(5, weight_list.get(5)+2);}
                else
                {weight_list.set(0, weight_list.get(0)+2);
                    weight_list.set(4, weight_list.get(4)+2);}
                if(common_list.get(4)==1)
                {weight_list.set(0, weight_list.get(0)+2);
                    weight_list.set(5, weight_list.get(5)+2);}
                else
                {weight_list.set(1, weight_list.get(1)+2);
                    weight_list.set(3, weight_list.get(3)+2);}
                if(common_list.get(5)==1)
                {weight_list.set(0, weight_list.get(0)+2);
                    weight_list.set(1, weight_list.get(1)+2);}
                else
                {weight_list.set(2, weight_list.get(2)+2);
                    weight_list.set(4, weight_list.get(4)+2);}
            }
            else if(user_selected_category.equals("shopping")){
                //첫번째 공통질문에서 답이 1일경우 카테고리별질문 5,1에 가중치 더함
                if(common_list.get(0)==1)
                {weight_list.set(1, weight_list.get(1)+2);
                    weight_list.set(2, weight_list.get(2)+2);}
                //첫번째 공통질문에서 답이 0일경우 카테고리별질문 3,6에 가중치 더함
                else
                {weight_list.set(0, weight_list.get(0)+2);
                    weight_list.set(3, weight_list.get(3)+2);}
                //두번째 공통질문에서 답이 1일경우 카테고리별질문 2,6에 가중치 더함
                if(common_list.get(1)==1)
                {weight_list.set(4, weight_list.get(4)+2);
                    weight_list.set(5, weight_list.get(5)+2);}
                //이하 동일
                else
                {weight_list.set(0, weight_list.get(0)+2);
                    weight_list.set(3, weight_list.get(3)+2);}
                if(common_list.get(2)==1)
                {weight_list.set(4, weight_list.get(4)+2);
                    weight_list.set(5, weight_list.get(5)+2);}
                else
                {weight_list.set(2, weight_list.get(2)+2);
                    weight_list.set(3, weight_list.get(3)+2);}
                if(common_list.get(3)==1)
                {weight_list.set(0, weight_list.get(0)+2);
                    weight_list.set(3, weight_list.get(3)+2);}
                else
                {weight_list.set(1, weight_list.get(1)+2);
                    weight_list.set(4, weight_list.get(4)+2);}
                if(common_list.get(4)==1)
                {weight_list.set(0, weight_list.get(0)+2);
                    weight_list.set(4, weight_list.get(4)+2);}
                else
                {weight_list.set(2, weight_list.get(2)+2);
                    weight_list.set(5, weight_list.get(5)+2);}
                if(common_list.get(5)==1)
                {weight_list.set(1, weight_list.get(1)+2);
                    weight_list.set(2, weight_list.get(2)+2);}
                else
                {weight_list.set(1, weight_list.get(1)+2);
                    weight_list.set(4, weight_list.get(4)+2);}
            }

            System.out.println("현제 카테고리별 가중치의 값은 "+list);
            System.out.println("최종적으로 더할 공통질문의 값은 "+weight_list);
            for(int i = 0; i < list.size(); i++){
                list.set(i, list.get(i)+weight_list.get(i));
            }
            System.out.println("최종적인 가중치는 "+list);

            //최종적으로 골라진 구독 서비스의 이름
            String select_name = "";

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
            System.out.println("list클리어 세션값은" + list);
            session.setAttribute("weight",list);
            //비어있는 배열을 집어넣어서 공통질문도 다시 사용할수있게함
            session.setAttribute("common_weight",list);
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

    @GetMapping("/recommend_common")
    public String recommend_common(Model model, HttpServletRequest req, HttpServletResponse res)
    {
        //공통질문 컨트롤러에 추가
        List<String> q_list = new ArrayList<>();
        q_list.add(common.q1);
        q_list.add(common.q2);
        q_list.add(common.q3);
        q_list.add(common.q4);
        q_list.add(common.q5);
        q_list.add(common.q6);
        //공통질문의 부가질문 컨트롤러에 추가
        List<String> q_q_list = new ArrayList<>();
        q_q_list.add(common.q1_1);
        q_q_list.add(common.q1_2);
        q_q_list.add(common.q2_1);
        q_q_list.add(common.q2_2);
        q_q_list.add(common.q3_1);
        q_q_list.add(common.q3_2);
        q_q_list.add(common.q4_1);
        q_q_list.add(common.q4_2);
        q_q_list.add(common.q5_1);
        q_q_list.add(common.q5_2);
        q_q_list.add(common.q6_1);
        q_q_list.add(common.q6_2);

        //get으로 받은 카테고리 추가
        String qcategory = req.getParameter("subscribe_category");
        model.addAttribute("category", qcategory);
        //첫번째 질문시
        if(req.getParameter("common_query_count")==null && req.getParameter("common_weight") == null)
        {
            //첫번째 질문 출력
            String query = q_list.get(0);
            String q_query_1 = q_q_list.get(0);
            String q_query_2 = q_q_list.get(1);

            model.addAttribute("common_query_count", 1);
            model.addAttribute("query", query);
            model.addAttribute("q_query_1", q_query_1);
            model.addAttribute("q_query_2", q_query_2);
            return "recommend_common";
        }



        //get으로 받은 출력한 질문의 번호
        int common_query_count = Integer.parseInt(req.getParameter("common_query_count"));
        //get으로 받은 유저가 입력한 선호도
        int common_weight = Integer.parseInt(req.getParameter("common_weight"));
        //세션에 weight값 저장 해야함
        HttpSession session = req.getSession();
        //유저의 답변을 저장하는 세션생성, 컨트롤러에도 세션값 받아 저장
        ArrayList<Integer> list_common = (ArrayList)session.getAttribute("common_weight");
        //첫 접속이라 list_common이 없다면 세션생성함
        if(list_common==null){
            list_common = new ArrayList<Integer>();
            session.setAttribute("common_weight",list_common);
        }

        //세션에 weight값 추가
        list_common.add(common_weight);
        //세션 콘솔창에 추가
        System.out.println(list_common);
        System.out.println(common_query_count);

        //뒤로가기를 했다가 다시하는 등 list개수와 질문개수 안맞을때 인덱스페이지로 이동함
        if(common_query_count != list_common.size()+1){
            //다시 이 페이지에서 접속해도 사용할수있게 list클리어 후 세션에 반영함
            list_common.clear();
            session.setAttribute("common_weight",list_common);
            return "index";
        }

        //마지막질문이면 값 넘어감
        if(common_query_count == 7){
            return "onlyredirectpage";
        }




        //모델에 query 전달, 구조상 사이즈 넘어가는 것은""로 처리했음
        if(common_query_count == q_list.size()+1) {
            String q_query_1 = q_q_list.get(2*(common_query_count-1));
            String q_query_2 = "";
            model.addAttribute("query", "");
            model.addAttribute("q_query_1", "");
            model.addAttribute("q_query_2", "");
        }
        else {
            String q_query_1 = q_q_list.get(2*(common_query_count-1));
            String q_query_2 = q_q_list.get(2*(common_query_count-1)+1);
            model.addAttribute("query", q_list.get(common_query_count - 1));
            model.addAttribute("q_query_1", q_query_1);
            model.addAttribute("q_query_2", q_query_2);
        }

        //모델에 count 전달
        model.addAttribute("common_query_count", common_query_count);

        return "recommend_common";
    }
}
