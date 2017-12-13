package com.aptech.itblog.service;

import com.aptech.itblog.collection.Post;

public interface PostService {
    boolean createPost(Post post);

    Post getPost(String post_id);

    boolean updatePost(Post post);

    boolean deletePost(String post_id);
}
