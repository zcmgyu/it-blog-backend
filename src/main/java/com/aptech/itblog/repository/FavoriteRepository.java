package com.aptech.itblog.repository;

import com.aptech.itblog.collection.Favorite;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FavoriteRepository extends MongoRepository<Favorite, String> {
}
