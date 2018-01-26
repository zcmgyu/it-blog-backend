package com.aptech.itblog.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Document(collection = "Post")
@JsonIgnoreProperties(value = {"createdAt"}, allowGetters = true)
public class Post {
    @Id
    private String id;

    @DBRef
    private User author;

    private String title;

    @NotNull
    private Object content;

    private String rawContent;

    private String image;

    @NotNull
    private String categoryId;

    private List<String> tags;

    @DBRef
    private List<User> favorite;

    private boolean status;

    private boolean publicPost;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date modifiedAt;

    public Post() {
    }

    public Post(User author, String title, Object content, String rawContent, String image, String categoryId, List<String> tags, List<User> favorite, boolean status, boolean publicPost) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.rawContent = rawContent;
        this.image = image;
        this.categoryId = categoryId;
        this.tags = tags;
        this.favorite = favorite;
        this.status = status;
        this.publicPost = publicPost;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Post) obj).getId().equals(this.id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isPublicPost() {
        return publicPost;
    }

    public void setPublicPost(boolean publicPost) {
        this.publicPost = publicPost;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRawContent() {
        return rawContent;
    }

    public void getRawContent(String rawContent) {
        this.rawContent = rawContent;
    }

    public void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }

    public List<User> getFavorite() {
        return favorite;
    }

    public void setFavorite(List<User> favorite) {
        this.favorite = favorite;
    }
}
