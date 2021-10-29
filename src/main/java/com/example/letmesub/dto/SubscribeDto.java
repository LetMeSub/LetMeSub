package com.example.letmesub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
public class SubscribeDto
{
    private String subscribe_name;
    private String subscribe_describe;
    private int subscribe_count;
    private String subscribe_category;
    private int subscribe_weight; //추천 가중치
    private int subscribe_rate; //육각형 평가 가중치
}
