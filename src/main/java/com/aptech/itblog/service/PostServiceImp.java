package com.aptech.itblog.service;

import com.aptech.itblog.collection.Post;
import com.aptech.itblog.model.Pagination;
import com.aptech.itblog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Post> getListPost(Pagination pagination) {
        Pageable pageable = new PageRequest(pagination.getPage(), pagination.getSize());
        return postRepository.findAll(pageable).getContent();
    }

    @Override
    public Post getPost(String post_id) {
        return postRepository.findOne(post_id);
    }

    @Override
    public boolean updatePost(Post post) {
        postRepository.save(post);
        return true;
    }

    @Override
    public boolean deletePost(String post_id) {
        postRepository.delete(post_id);

        if (null != getPost(post_id)) {
            return true;
        }
        return false;
    }


}
