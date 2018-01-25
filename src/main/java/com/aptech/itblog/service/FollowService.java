package com.aptech.itblog.service;

import com.aptech.itblog.collection.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FollowService {

    String toggleFollow(String targetUserId);

    Page<User> getFollowing(String userId, Pageable pageable);

    Page<User> getFollowers(String userId, Pageable pageable);
}
