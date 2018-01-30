package com.aptech.itblog.service;

import com.aptech.itblog.collection.Post;
import com.aptech.itblog.collection.User;
import com.aptech.itblog.repository.PostRepository;
import com.aptech.itblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public LinkedHashMap<String, Page> search(String search, Pageable pageable) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(search);
        Page<Post> postPage = postRepository.findAllBy(criteria, pageable);
        Page<User> userPage = userRepository.findAllBy(criteria, pageable);

        LinkedHashMap<String, Page> searchMap = new LinkedHashMap() {
            {
                put("posts", postPage);
                put("users", userPage);
            }
        };
        return searchMap;
    }

}
