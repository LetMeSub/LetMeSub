package com.example.letmesub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
public class CommentDto
{
    int comment_num;
    String comment_context;
    String comment_user_id;
    String comment_subscribe_name;


}
