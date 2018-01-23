package com.aptech.itblog.service;

import com.aptech.itblog.collection.Comment;
import com.aptech.itblog.collection.Post;
import com.aptech.itblog.exception.DataAccessException;
import com.aptech.itblog.model.Pagination;

import java.util.List;

public interface CommentService {

    void addComment(Comment comment) throws DataAccessException;

    List<Comment> getAllComment();

    Comment getCommentById(String commentId);

    List<Comment> getAllReply();

    void updateComment(Comment comment);

}
