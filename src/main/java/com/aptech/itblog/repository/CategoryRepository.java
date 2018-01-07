package com.aptech.itblog.repository;

import com.aptech.itblog.collection.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
