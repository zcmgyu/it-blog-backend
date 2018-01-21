package com.aptech.itblog.model;

import com.aptech.itblog.collection.Category;
import com.aptech.itblog.collection.Post;

import java.util.List;

public class PostByCategoryDTO {
    private String id;

    private String category;

    private List<PostDTO> top4;

    public PostByCategoryDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<PostDTO> getTop4() {
        return top4;
    }

    public void setTop4(List<PostDTO> top4) {
        this.top4 = top4;
    }
}
