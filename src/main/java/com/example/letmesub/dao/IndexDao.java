package com.example.letmesub.dao;

import com.example.letmesub.dto.SubscribeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IndexDao
{
    List<SubscribeDto> IndexList(String category);
    List<SubscribeDto> IndexAllList();
}
