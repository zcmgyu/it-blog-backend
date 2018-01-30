package com.aptech.itblog.repository;


import com.aptech.itblog.collection.Post;
import com.aptech.itblog.collection.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findById(String userId);
    User findByUsername(String username);
    User findByEmail(String email);
    User findByResetToken(String resetToken);
    Page<User> findAllByUsernameOrNameOrEmailContains(String username, String name, String email, Pageable pageable);

    Page<User> findAllBy(TextCriteria criteria, Pageable pageable);
}

