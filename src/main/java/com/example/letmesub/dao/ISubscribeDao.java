package com.example.letmesub.dao;

import com.example.letmesub.dto.SubscribeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ISubscribeDao {
    List<SubscribeDto> list(String category);
    int viewCount(String name);
    int updateCount(String name);
    SubscribeDto viewAll(String name);
}
