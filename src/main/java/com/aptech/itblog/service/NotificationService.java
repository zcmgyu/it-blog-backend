package com.aptech.itblog.service;

import com.aptech.itblog.collection.Notification;

import java.util.List;


public interface NotificationService {
    void notify(Notification notification, String username);

    void save(Notification notification);

    List<Notification> getTop10();
}
