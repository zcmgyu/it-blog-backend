package com.example.itblog.repository;

import com.example.itblog.model.Follow;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository  extends MongoRepository<Follow, String> {
}
