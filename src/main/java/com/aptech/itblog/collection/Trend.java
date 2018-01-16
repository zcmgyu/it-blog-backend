package com.aptech.itblog.collection;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Trend {
    @Id
    private String id;

    private Date activedDate;

    private String title;

    private String path;

    private long views;

    public Trend() {
    }

    public Trend(Date activedDate, String title, String path, long views) {
        this.activedDate = activedDate;
        this.title = title;
        this.path = path;
        this.views = views;
    }

    // GETTER AND SETTER

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getActivedDate() {
        return activedDate;
    }

    public void setActivedDate(Date activedDate) {
        this.activedDate = activedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }
}
