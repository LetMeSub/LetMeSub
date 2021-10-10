package com.example.letmesub.recommend;//같은점수의 서비스 처리문제 수정필요(현재는 모두출력)
//불필요하게 코드가 많은 부분 수정필요

public class recAlgo {

    public static class service{
        String name; //서비스의 이름

        int score; //최종점수

        int factor_1; //각 요소별 점수
        int factor_2;
        int factor_3;
        int factor_4;
        int factor_5;
        int factor_6;

        service(String name, //이름과 점수 설정
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
        void set_score(int uw1, int uw2, int uw3, int uw4, int uw5, int uw6){
            score = factor_1*uw1 + factor_2*uw2 + factor_3*uw3 +
                    factor_4*uw4 + factor_5*uw5 + factor_6*uw6;
        }


    }
    public static class set_user_weight{
        int user_factor1; //사용자 선호도
        int user_factor2;
        int user_factor3;
        int user_factor4;
        int user_factor5;
        int user_factor6;

        set_user_weight( //사용자 선호도 설정
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
        int winner;
        void sort(int score_1, int score_2, int score_3,
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

    public static void main(String[] args) {
        service service1 = new service("넷플릭스", 1,2,
                3,4,5,6);
        service service2 = new service("디즈니", 6,5,
                1,2,3,4);
        service service3 = new service("왓챠", 2,1,
                6,5,4,3);
        service service4 = new service("훌루", 5,4,
                1,2,3,6);
        service service5 = new service("아마존프라임", 3,2,
                5,6,4,1);

        set_user_weight value = new set_user_weight(1,3,7,6,8,1);//사용자 선호도

        //선호도와 가중치 고려하여 최종점수 산출
        service1.set_score(value.user_factor1, value.user_factor2, value.user_factor3,
                value.user_factor4, value.user_factor5, value.user_factor6);

        service2.set_score(value.user_factor1, value.user_factor2, value.user_factor3,
                value.user_factor4, value.user_factor5, value.user_factor6);

        service3.set_score(value.user_factor1, value.user_factor2, value.user_factor3,
                value.user_factor4, value.user_factor5, value.user_factor6);

        service4.set_score(value.user_factor1, value.user_factor2, value.user_factor3,
                value.user_factor4, value.user_factor5, value.user_factor6);

        service5.set_score(value.user_factor1, value.user_factor2, value.user_factor3,
                value.user_factor4, value.user_factor5, value.user_factor6);


        sort_algo select_service = new sort_algo();
        select_service.sort(
                service1.score, service2.score, service3.score,
                service4.score, service5.score);

        if (service1.score == select_service.winner){
            System.out.printf("내가 선택한 서비스는 " + service1.name);
        }
        if (service2.score == select_service.winner){
            System.out.printf("내가 선택한 서비스는 " + service2.name);
        }
        if (service3.score == select_service.winner){
            System.out.printf("내가 선택한 서비스는 " + service3.name);
        }
        if (service4.score == select_service.winner){
            System.out.printf("내가 선택한 서비스는 " + service4.name);
        }
        if (service5.score == select_service.winner){
            System.out.printf("내가 선택한 서비스는 " + service5.name);
        }

    }



}
