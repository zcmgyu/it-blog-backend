package com.aptech.itblog.repository;

import com.aptech.itblog.collection.Follow;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository  extends MongoRepository<Follow, String> {
}
