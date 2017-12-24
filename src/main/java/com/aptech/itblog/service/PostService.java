package com.aptech.itblog.service;

import com.aptech.itblog.collection.Post;
import com.aptech.itblog.model.Pagination;

import java.util.List;


public interface PostService {
    boolean createPost(Post post);

    List<Post> getListPost(Pagination pagination);

    Post getPost(String post_id);

    boolean updatePost(Post post);

    boolean deletePost(String post_id);
}
