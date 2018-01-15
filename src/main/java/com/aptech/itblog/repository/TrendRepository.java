package com.aptech.itblog.repository;

import com.aptech.itblog.collection.Trend;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrendRepository extends MongoRepository<Trend, String> {
}
