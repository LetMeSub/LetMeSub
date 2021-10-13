package com.example.letmesub.dao;

import com.example.letmesub.dto.User;

import java.util.List;


public interface UserDao
{
    List<User> selectUsers();
    void insertUser(User user);
}
