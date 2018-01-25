package com.aptech.itblog.service;

import com.aptech.itblog.collection.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookmarkService {
    Page<Post> getBookmarks(Pageable pageable);

    String toggleBookmark(String targetPost);
}
