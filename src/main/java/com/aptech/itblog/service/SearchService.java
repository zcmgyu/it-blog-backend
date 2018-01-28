package com.aptech.itblog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedHashMap;

public interface SearchService {
    LinkedHashMap<String, Page> search(String search, Pageable pageable);
}
