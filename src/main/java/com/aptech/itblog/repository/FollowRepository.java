package com.aptech.itblog.repository;

import com.aptech.itblog.collection.Follow;
import com.aptech.itblog.collection.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FollowRepository extends MongoRepository<Follow, String> {
    Follow findByUserId(String userId);

    List<Follow> findByFollowing(User user);

    List<Follow> findByFollowingIn(List<User> users);

}
