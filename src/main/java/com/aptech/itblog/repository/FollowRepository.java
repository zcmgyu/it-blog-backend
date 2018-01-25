package com.aptech.itblog.repository;

import com.aptech.itblog.collection.Follow;
import com.aptech.itblog.collection.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FollowRepository extends MongoRepository<Follow, String> {
    Follow findByUserId(String userId);

    Page<Follow> findAllByFollowing(User user, Pageable pageable);

}
