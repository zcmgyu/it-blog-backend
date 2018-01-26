package com.aptech.itblog.repository;

import com.aptech.itblog.collection.Love;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoveRepository extends MongoRepository<Love, String> {
}
