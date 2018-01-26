package com.aptech.itblog.service;

import com.aptech.itblog.collection.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FavoriteService {
    Page<Post> getFavorites(Pageable pageable);

    String toggleFavorite(String targetPost);
}
