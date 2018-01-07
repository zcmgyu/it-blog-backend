package com.aptech.itblog.service;

import com.aptech.itblog.collection.Post;
import com.aptech.itblog.model.Pagination;
import com.aptech.itblog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImp implements PostService {

    @Autowired
    PostRepository postRepository;

    @Override
    public boolean createPost(Post post) {
        postRepository.save(post);
        return true;
    }

    @Override
    public Page<Post> getPagePost(Pageable pageable) {
        return postRepository.findAll(pageable);
    }


    @Override
    public Post getPost(String postId) {
        return postRepository.findOne(postId);
    }

    @Override
    public boolean updatePost(Post post) {
        postRepository.save(post);
        return true;
    }

    @Override
    public boolean deletePost(String postId) {
        postRepository.delete(postId);

        if (null != getPost(postId)) {
            return true;
        }
        return false;
    }

    @Override
    public List<Post> getListPost() {
        return postRepository.findAll();
    }
}
