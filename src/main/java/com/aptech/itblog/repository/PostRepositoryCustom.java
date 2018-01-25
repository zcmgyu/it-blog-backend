package com.aptech.itblog.repository;

import com.aptech.itblog.model.PostByCategory;

import java.util.List;

public interface PostRepositoryCustom {
    List<PostByCategory> getLatestPostGroupByCategory();

    List<PostByCategory> getTrendPostGroupByCategory();
}
