package com.aptech.itblog.controller;

import com.aptech.itblog.collection.Follow;
import com.aptech.itblog.collection.User;
import com.aptech.itblog.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.aptech.itblog.common.CollectionLink.API;
import static com.aptech.itblog.common.CollectionLink.FOLLOW_USER_ID;

@RestController
@RequestMapping(value = API)
public class FollowController {
    @Autowired
    private FollowRepository followRepository;

    @PutMapping(value = FOLLOW_USER_ID)
    public ResponseEntity<?> followUser(@PathVariable(value = "user_id") String userId) {
        // Set author id
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Follow followUser = followRepository.findByUserId(user.getId());

        Follow followedUser = followRepository.findByUserId(userId);

        if (followUser == null) {

        }
        return null;
    }
}
