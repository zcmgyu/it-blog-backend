package com.aptech.itblog.model;

import com.aptech.itblog.collection.Post;

import java.util.List;

public class PostByCategory {
    private String _id;

    private String category;

    private List<Post> top_4;

    public PostByCategory() {
    }

    public PostByCategory(String _id, String category, List<Post> top_4) {
        this._id = _id;
        this.category = category;
        this.top_4 = top_4;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Post> getTop_4() {
        return top_4;
    }

    public void setTop_4(List<Post> top_4) {
        this.top_4 = top_4;
    }
}
