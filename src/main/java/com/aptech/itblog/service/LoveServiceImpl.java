package com.aptech.itblog.service;

import com.aptech.itblog.collection.Bookmark;
import com.aptech.itblog.collection.Post;
import com.aptech.itblog.collection.User;
import com.aptech.itblog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoveServiceImpl implements LoveService {
    @Autowired
    PostRepository postRepository;

    @Override
    public Page<Post> getBookmarks(Pageable pageable) {
        return null;
    }

    @Override
    public String toggleBookmark(String targetPostId) {
        // Create User
        // Set author id
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Target Post
        Post targetPost = postRepository.findById(targetPostId);

        List<User> lovedList = targetPost.getLoved();

        if (lovedList == null) lovedList = new ArrayList<>();

        String[] messageArr = new String[1];

        String username = currentUser.getUsername();
        // Toggle bookmark
        if (lovedList.contains(currentUser)) {
            lovedList.remove(currentUser);
            messageArr[0] = "You removed " + username + " from loved.";
        } else {
            lovedList.add(currentUser);
            messageArr[0] = "You added " + username + " into loved.";
        }
        targetPost.setLoved(lovedList);
        // Save to DB
        postRepository.save(targetPost);
        return messageArr[0];
    }
}
