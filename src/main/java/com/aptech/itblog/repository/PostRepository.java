package com.aptech.itblog.repository;

import com.aptech.itblog.collection.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findTop4ByCategoryIdAndPublicPostInOrderByCreateAtDesc(String categoryId, boolean publicPost);
}
