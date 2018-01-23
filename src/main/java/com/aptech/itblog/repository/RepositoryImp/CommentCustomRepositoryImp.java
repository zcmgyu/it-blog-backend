package com.aptech.itblog.repository.RepositoryImp;

import com.aptech.itblog.collection.Comment;
import com.aptech.itblog.collection.Post;
import com.aptech.itblog.exception.DataAccessException;
import com.aptech.itblog.repository.CommentCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class CommentCustomRepositoryImp implements CommentCustomRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Comment> getAllComment() throws DataAccessException {
        return mongoTemplate.find(Query.query(new Criteria()
                .andOperator(Criteria.where("isCommentDelete").exists(false),
                        Criteria.where("isComment").exists(true))
        ), Comment.class);
    }

    @Override
    public List<Comment> getAllReply() throws DataAccessException {
        return mongoTemplate.find(Query.query(new Criteria()
                .andOperator(Criteria.where("isCommentDelete").exists(false),
                        Criteria.where("isComment").exists(false))
        ), Comment.class);
    }

    @Override
    public Comment getCommentById(String commentId) {
        return mongoTemplate.findOne(Query.query(new Criteria()
                .andOperator(Criteria.where("isCommentDelete").exists(false),
                        Criteria.where("_id").regex(commentId))
        ), Comment.class);
    }
}
