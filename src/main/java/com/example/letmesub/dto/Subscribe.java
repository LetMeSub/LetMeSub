package com.example.letmesub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
public class Subscribe
{
    String subscribe_name;
    String subscribe_describe;
    String subscribe_category;
    String subscribe_imgPath;
    int subscribe_weight; //추천 가중치
    int subscribe_count;
    int subscribe_rate; //육각형 평가 가중치
}
