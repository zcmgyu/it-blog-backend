package com.aptech.itblog.repository;

import com.aptech.itblog.collection.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
    Post findById(String id);

    Page<Post> findAllByAuthorId(String authorId, Pageable pageable);

    Page<Post> findAllByTitleOrRawContentContains(String search1, String search2, Pageable pageable);

    Page<Post> findAllByTagsContains(String tag, Pageable pageable);
}
