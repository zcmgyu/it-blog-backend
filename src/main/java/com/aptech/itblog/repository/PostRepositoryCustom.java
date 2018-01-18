package com.aptech.itblog.repository;

import java.util.LinkedHashMap;
import java.util.List;

public interface PostRepositoryCustom {
    List<Object> getLatestPostGroupByCategory();
}
