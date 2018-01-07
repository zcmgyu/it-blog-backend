package com.aptech.itblog.service;

import com.aptech.itblog.collection.Post;
import com.aptech.itblog.model.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PostService {
    boolean createPost(Post post);

    List<Post> getListPost();

    Page<Post> getPagePost(Pageable pageable);

    Post getPost(String postId);

    boolean updatePost(Post post);

    boolean deletePost(String postId);
}
