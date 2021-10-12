package com.example.letmesub.recommend;

import com.example.letmesub.LetMeSubApplication;
import org.springframework.boot.SpringApplication;

import java.util.ArrayList;

public class recAlgo2 {
    
    //구독서비스 저장 클래스
    public static class subscribe{
        //구독서비스 이름
        private String s_name;
        //구독서비스 평가 가중치
        private int s_weight;
        //유저 가중치와 연산후 최종 가중치
        private int s_u_weight;
        //set
        public void set_s_name(String s_name){
            this.s_name = s_name;
        }
        public void set_s_weight(int s_weight){
            this.s_weight = s_weight;
        }
        public void set_s_u_weight(int s_u_weight){
            this.s_u_weight = s_u_weight;
        }
        //get
        public String get_s_name(){
            return s_name;
        }
        public int get_s_weight(){
            return s_weight;
        }
        public int get_s_u_weight(){
            return s_u_weight;
        }
    }
}


