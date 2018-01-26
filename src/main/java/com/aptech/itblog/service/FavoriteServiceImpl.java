package com.aptech.itblog.service;

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
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    PostRepository postRepository;

    @Override
    public Page<Post> getFavorites(Pageable pageable) {
        return null;
    }

    @Override
    public String toggleFavorite(String targetPostId) {
        // Create User
        // Set author id
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Target Post
        Post targetPost = postRepository.findById(targetPostId);

        List<User> favoriteList = targetPost.getFavorite();

        if (favoriteList == null) favoriteList = new ArrayList<>();

        String[] messageArr = new String[1];

        String username = currentUser.getUsername();
        // Toggle bookmark
        if (favoriteList.contains(currentUser)) {
            favoriteList.remove(currentUser);
            messageArr[0] = "You removed " + username + " from favorite.";
        } else {
            favoriteList.add(currentUser);
            messageArr[0] = "You added " + username + " into favorite.";
        }
        targetPost.setFavorite(favoriteList);
        // Save to DB
        postRepository.save(targetPost);
        return messageArr[0];
    }
}
