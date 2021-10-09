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
    int subscribe_weight;
    int subscribe_count;
}
