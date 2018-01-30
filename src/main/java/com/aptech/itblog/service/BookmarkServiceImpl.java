package com.aptech.itblog.service;

import com.aptech.itblog.collection.Bookmark;
import com.aptech.itblog.collection.Notification;
import com.aptech.itblog.collection.Post;
import com.aptech.itblog.collection.User;
import com.aptech.itblog.repository.BookmarkRepository;
import com.aptech.itblog.repository.PostRepository;
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
public class BookmarkServiceImpl implements BookmarkService {
    @Autowired
    public BookmarkRepository bookmarkRepository;

    @Autowired
    public PostRepository postRepository;

    @Override
    public Page<Post> getBookmarks(Pageable pageable) {
        // Set author id
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Bookmark bookmark = bookmarkRepository.findByUserId(user.getId());

        if (bookmark == null) return null;

        List<Post> posts = bookmark.getPosts();

        // Change to post page
        Page<Post> postPage = new PageImpl<>(posts, pageable, posts.size());
        return postPage;
    }

    @Override
    public String toggleBookmark(String targetPostId) {
        // Create User
        // Set author id
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Bookmark bookmark = bookmarkRepository.findByUserId(currentUser.getId());

        // TargetUser
        Post targetPost = postRepository.findById(targetPostId);

        if (bookmark == null) bookmark = new Bookmark(currentUser, new ArrayList<>());

        List<Post> postList = bookmark.getPosts();

        String[] messageArr = new String[1];

        // Toggle bookmark
        if (postList.contains(targetPost)) {
            postList.remove(targetPost);
            messageArr[0] = "You removed " + targetPostId + " from bookmarks.";
        } else {
            postList.add(targetPost);
            messageArr[0] = "You added " + targetPostId + " into bookmarks.";
        }
        bookmark.setPosts(postList);
        // Save to DB
        bookmarkRepository.save(bookmark);
        return messageArr[0];
    }
}
