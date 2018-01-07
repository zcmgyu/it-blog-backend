package com.aptech.itblog.service.ServiceImp;

import com.aptech.itblog.collection.Post;
import com.aptech.itblog.collection.User;
import com.aptech.itblog.model.Pagination;
import com.aptech.itblog.repository.PostRepository;
import com.aptech.itblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.matching;

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
        return null;
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

        if(null != getPost(post_id)){
            return true;
        }
        return false;
    }

    @Override
    public List<Post> getListPostByContent(Pagination pagination, String searchText) {

        Example<Post> example = Example.of(new Post("*", "*"),
                matching().withStringMatcher(StringMatcher.REGEX));

        postRepository.findAll(example);
//        assertThat(userRepository.findAll(example), not(containsInAnyOrder(hank, marie)));
//        TextCriteria criteria = TextCriteria.forDefaultLanguage()
//                .matchingAny(searchText);

//        Query query = TextQuery.queryText(criteria)
//                .sortByScore()
//                .with(new PageRequest(0, 5));
//
//        List<User> recipes = template.find(query, User);
        return null;

    }

}