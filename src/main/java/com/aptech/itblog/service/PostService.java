package com.aptech.itblog.service;

import com.aptech.itblog.collection.Category;
import com.aptech.itblog.collection.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedHashMap;
import java.util.List;


public interface PostService {
    Post createPost(Post post);

    Page<Post> getPagePost(Pageable pageable);

    LinkedHashMap<String, List<Post>> getTop4ByCategory();

    Post getPost(String postId);

    Post updatePost(String id, Post post);

    boolean deletePost(String postId);
}
