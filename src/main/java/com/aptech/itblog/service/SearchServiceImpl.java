package com.aptech.itblog.service;

import com.aptech.itblog.collection.Post;
import com.aptech.itblog.collection.User;
import com.aptech.itblog.repository.PostRepository;
import com.aptech.itblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public LinkedHashMap<String, ?> search(String search, Pageable pageable) {
        Page<Post> postPage = postRepository.findAllByTitleOrRawContentContains(search, search, pageable);
        Page<User> userPage = userRepository.findAllByUsernameOrNameOrEmailContains(search, search, search, pageable);

        LinkedHashMap<String, ?> searchMap = new LinkedHashMap() {
            {
                put("posts", postPage);
                put("users", userPage);
            }
        };
        return searchMap;
    }

}
