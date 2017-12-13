package com.aptech.itblog.repository;

import com.aptech.itblog.model.Bookmark;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends MongoRepository<Bookmark, String> {
}
