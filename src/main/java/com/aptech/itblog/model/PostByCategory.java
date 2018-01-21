package com.aptech.itblog.model;

import com.aptech.itblog.collection.Category;
import com.aptech.itblog.collection.Post;
import com.mongodb.DBObject;

import java.util.List;

public class PostByCategory {
    private String _id;

    private List<Post> top4;

    public PostByCategory() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<Post> getTop4() {
        return top4;
    }

    public void setTop4(List<Post> top4) {
        this.top4 = top4;
    }
}