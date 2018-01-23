package com.aptech.itblog.repository;

import com.aptech.itblog.collection.Comment;
import com.aptech.itblog.exception.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentCustomRepository {

    List<Comment> getAllComment() throws DataAccessException;

    List<Comment> getAllReply() throws  DataAccessException;

    Comment getCommentById(String commentId);
}
