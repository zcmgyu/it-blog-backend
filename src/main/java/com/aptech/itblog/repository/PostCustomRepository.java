package com.aptech.itblog.repository;

import com.aptech.itblog.collection.Post;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PostCustomRepository {

    Collection<Post> searchPost(String text);

    Collection<Post> searchbycategory(String categoryText);
}
