package com.aptech.itblog.repository.RepositoryImp;

import com.aptech.itblog.collection.Category;
import com.aptech.itblog.collection.Post;
import com.aptech.itblog.repository.PostCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collection;

public class PostCustomRepositoryImp implements PostCustomRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Collection<Post> searchPost(String text) {
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("title").regex(text, "i"),
                        Criteria.where("content").regex(text, "i"))
        ), Post.class);
    }

    @Override
    public Collection<Post> searchbycategory(String categoryText) {
        Category category = mongoTemplate.findOne(Query.query(new Criteria()
                .orOperator(Criteria.where("categoryName").regex(categoryText, "i"))
        ), Category.class);
        return mongoTemplate.find(Query.query(new Criteria()
                .orOperator(Criteria.where("categoryId").regex(category.get_id(), "i"))
        ), Post.class);
    }
}
