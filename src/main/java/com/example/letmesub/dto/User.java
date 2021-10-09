package com.example.letmesub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@Component
public class User
{
    String User_id;
    String User_pw;
    String User_email;
}
