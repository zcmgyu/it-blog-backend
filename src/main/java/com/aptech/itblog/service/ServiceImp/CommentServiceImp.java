package com.aptech.itblog.service.ServiceImp;

import com.aptech.itblog.collection.Comment;
import com.aptech.itblog.exception.DataAccessException;
import com.aptech.itblog.repository.CommentCustomRepository;
import com.aptech.itblog.repository.CommentRepository;
import com.aptech.itblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;

@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentCustomRepository commentCustomRepository;

    @Override
    public void addComment(Comment comment) throws DataAccessException {
        commentRepository.save(comment);
    }

    @Override
    public void updateComment(Comment comment) throws DataAccessException {
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllComment() throws DataAccessException{
        return commentCustomRepository.getAllComment();
    }

    @Override
    public Comment getCommentById(String commentId) throws DataAccessException {
        return commentCustomRepository.getCommentById(commentId);
    }

    @Override
    public List<Comment> getAllReply() throws DataAccessException{
        return commentCustomRepository.getAllReply();
    }

}
