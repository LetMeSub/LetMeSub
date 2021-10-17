package com.example.letmesub.dao;

import com.example.letmesub.dto.SubscribeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ISubscribeDao {
    List<SubscribeDto> list();
}
