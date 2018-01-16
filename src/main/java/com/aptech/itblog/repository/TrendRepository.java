package com.aptech.itblog.repository;

import com.aptech.itblog.collection.Trend;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TrendRepository extends MongoRepository<Trend, String> {

    List<Trend> findAllByActiveDateAfter(Date date);
    List<Trend> findAllByActiveDateBefore(Date date);
}
