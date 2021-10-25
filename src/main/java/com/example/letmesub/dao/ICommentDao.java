package com.example.letmesub.dao;

import com.example.letmesub.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ICommentDao {
    List<CommentDto> commentList(String name);
    int insertComment(String context, String u_id, String subscribe);
}
