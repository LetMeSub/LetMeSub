package com.example.letmesub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@Component
public class Comment
{
    int comment_num;
    String comment_subscribe_name;
    String comment_user_id;
    String comment_context;




}
