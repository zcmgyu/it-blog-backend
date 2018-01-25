package com.aptech.itblog.repository;

import com.aptech.itblog.collection.Bookmark;
import com.aptech.itblog.collection.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookmarkRepository extends MongoRepository<Bookmark, String> {
    Bookmark findByUserId(String userId);

    Page<Bookmark> findAllByPosts(User user, Pageable pageable);
}
