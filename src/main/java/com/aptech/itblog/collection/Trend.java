package com.aptech.itblog.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Trend")
public class Trend {
    @Id
    private String id;

    private Date activeDate;

    private String title;

    private String postId;

    private long views;

    public Trend() {
    }

    public Trend(String title, String postId, long views, Date activeDate) {
        this.title = title;
        this.postId = postId;
        this.views = views;
        this.activeDate = activeDate;
    }

    // GETTER AND SETTER


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }
}
