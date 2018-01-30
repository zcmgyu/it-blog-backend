package com.aptech.itblog.repository;

import com.aptech.itblog.collection.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findTop10ByUserIdOrderByCreateAtDesc(String userId);
}
