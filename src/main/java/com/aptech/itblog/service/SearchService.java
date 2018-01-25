package com.aptech.itblog.service;

import org.springframework.data.domain.Pageable;

import java.util.LinkedHashMap;

public interface SearchService {
    LinkedHashMap<String, ?> search(String search, Pageable pageable);
}
