package com.aptech.itblog.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Trend")
public class Trend {
    @Id
    private String id;

    private Date activeDate;

    private String title;

    private String categoryId;

    @DBRef
    private Post post;

    private long views;

    public Trend() {
    }

    public Trend(String title, Post post, long views, String categoryId, Date activeDate) {
        this.activeDate = activeDate;
        this.title = title;
        this.categoryId = categoryId;
        this.post = post;
        this.views = views;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
