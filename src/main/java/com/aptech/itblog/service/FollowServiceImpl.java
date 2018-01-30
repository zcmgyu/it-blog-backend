package com.aptech.itblog.service;

import com.aptech.itblog.collection.Follow;
import com.aptech.itblog.collection.Notification;
import com.aptech.itblog.collection.User;
import com.aptech.itblog.repository.FollowRepository;
import com.aptech.itblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    FollowRepository followRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationService notificationService;

    @Override
    public String toggleFollow(String targetUserId) {
        // Create User
        // Set author id
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Follow follow = followRepository.findByUserId(currentUser.getId());

        // TargetUser
        User targetUser = userRepository.findById(targetUserId);

        if (follow == null)
            follow = new Follow(currentUser, new ArrayList<>());

        List<User> followingList = follow.getFollowing();

        String[] messageArr = new String[1];
        String targetUsername = targetUser.getUsername();
        // Toggle follow
        if (followingList.contains(targetUser)) {
            followingList.remove(targetUser);
            messageArr[0] = "You removed follow " + targetUsername;
        } else {
            followingList.add(targetUser);
            messageArr[0] = "You are following " + targetUsername;
            // Send notification
            String nofityMessage = currentUser.getUsername() + " is following you";
            Notification notification = new Notification(targetUser, nofityMessage, currentUser, null, new Date(), null);

            // Send to target user
            notificationService.notify(notification, targetUsername);
        }

        // Save to DB
        followRepository.save(follow);
        return messageArr[0];
    }

    @Override
    public Page<User> getFollowing(String userId, Pageable pageable) {

        Follow follow = followRepository.findByUserId(userId);

        if (follow == null) {
            return null;
        }

        List<User> followingList = follow.getFollowing();

        // In case following list null
        if (followingList == null) followingList = new ArrayList<>();

        // Create pageable
        Page<User> followingPage = new PageImpl<>(followingList, pageable, followingList.size());

        return followingPage;
    }

    @Override
    public Page<User> getFollowers(String userId, Pageable pageable) {
        // Create User
        User user = new User();
        user.setId(userId);

        // Pagination
        Page<Follow> followerPage = followRepository.findAllByFollowing(user, pageable);

        // Follower list
        Page<User> userPage = followerPage.map(follow -> follow.getUser());

        return userPage;
    }
}
