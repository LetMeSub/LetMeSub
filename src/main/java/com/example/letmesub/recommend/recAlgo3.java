package com.example.letmesub.recommend;

import java.util.ArrayList;

public class recAlgo3 {
    public static int[] find_num(int s_weight){

        int weight[] = new int[6];
        weight[0] = s_weight/100000;
        weight[1] = s_weight%100000/10000;;
        weight[2] = s_weight%10000/1000;;
        weight[3] = s_weight%1000/100;;
        weight[4] = s_weight%100/10;;
        weight[5] = s_weight%10;;
        return weight;
    }

    //추천 알고리즘
    public static int rec_algo(int sweight, ArrayList<Integer> u_weight){
        int s_weight[] = find_num(sweight);
        int s_u_weight = 0;
        for(int i = 0; i<u_weight.size(); i++)
        {
            s_u_weight = s_u_weight + s_weight[i]*u_weight.get(i);
        }

        return s_u_weight;
    }
}
