package com.aptech.itblog.service;

import com.aptech.itblog.collection.Post;
import com.aptech.itblog.model.PostByCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PostService {
    Post createPost(Post post);

    Page<Post> getPagePost(Pageable pageable);

    Page<Post> getPagePostByAuthorId(String userId, Pageable pageable);

    List<PostByCategory> getTop4LatestPostByCategory();

    List<PostByCategory> getTop4TrendPostByCategory();

    Post getPost(String postId);

    Post updatePost(String id, Post post);

    boolean deletePost(String postId);


}
