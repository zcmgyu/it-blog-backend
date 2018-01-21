package com.aptech.itblog.repository;

import com.aptech.itblog.model.PostByCategory;
import com.aptech.itblog.model.TrendViews;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface PostRepositoryCustom {
    List<PostByCategory> getLatestPostGroupByCategory();

    List<PostByCategory> getTrendPostGroupByCategory();
}
