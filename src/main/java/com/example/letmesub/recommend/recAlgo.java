package com.example.letmesub.recommend;
//같은점수의 서비스 처리문제 수정필요(현재는 모두출력)
//불필요하게 코드가 많은 부분 수정필요

public class recAlgo {

    public static class service{
        public String name; //서비스의 이름


        public int score; //최종점수
        
        int factor_1; //각 요소별 점수
        int factor_2;
        int factor_3;
        int factor_4;
        int factor_5;
        int factor_6;

        public service(String name, //이름과 점수 설정
                int weight1, int weight2, int weight3,
                int weight4, int weight5, int weight6) {
            this.name = name;
            factor_1 = weight1;
            factor_2 = weight2;
            factor_3 = weight3;
            factor_4 = weight4;
            factor_5 = weight5;
            factor_6 = weight6;
            
        }

        //사용자 선호도와 연산하여 최종점수 구함
        public void set_score(int uw1, int uw2, int uw3, int uw4, int uw5, int uw6){
            score = factor_1*uw1 + factor_2*uw2 + factor_3*uw3 +
                    factor_4*uw4 + factor_5*uw5 + factor_6*uw6;
        }

    }
    public static class set_user_weight{
        public int user_factor1; //사용자 선호도
        public int user_factor2;
        public int user_factor3;
        public int user_factor4;
        public int user_factor5;
        public int user_factor6;

        public set_user_weight( //사용자 선호도 설정
                int weight1, int weight2, int weight3,
                int weight4, int weight5, int weight6) {
            user_factor1 = weight1;
            user_factor2 = weight2;
            user_factor3 = weight3;
            user_factor4 = weight4;
            user_factor5 = weight5;
            user_factor6 = weight6;

        }
    }

    public static class sort_algo{ //정렬알고리즘
        public int winner;
        public void sort(int score_1, int score_2, int score_3,
                  int score_4, int score_5){
            winner = score_1;

            int[] participant = {score_1, score_2, score_3, score_4, score_5};
            for(int i = 0; i < participant.length; i++){
                if(winner < participant[i]){
                    winner = participant[i];
                }
            }
        }
    }
}
