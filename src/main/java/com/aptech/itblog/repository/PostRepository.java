package com.aptech.itblog.repository;

import com.aptech.itblog.collection.Post;
import com.aptech.itblog.collection.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {
    Post findById(String id);

    Page<Post> findAllByAuthorId(String authorId, Pageable pageable);

    List<Post> findAllByAuthorId(String authorId);

    List<Post> findAllByAuthor(User author);
}
