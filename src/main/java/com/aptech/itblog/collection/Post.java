package com.aptech.itblog.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.format.annotation.DateTimeFormat;

@QuerydslPredicate
@Document(collection = "Post")
@JsonIgnoreProperties(value = {"createdAt", "modifiedAt"}, allowGetters = true)
public class Post {

    @Id
    private String id;

    @NotEmpty
    private String authorId;

    private @TextIndexed(weight = 2) String title;

    private @TextIndexed String content;

    private String url;

    private boolean status;

    private boolean publicPost;

    private String categoryId;

    @NotEmpty
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createAt;


    @NotEmpty
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date modifiedAt;

    public Post() {
    }

    public Post(String authorId, String title, String content, String url, boolean status, boolean publicPost, String categoryId) {
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.url = url;
        this.status = status;
        this.publicPost = publicPost;
        this.categoryId = categoryId;
    }

    public Post(String authorId, String title) {
        this.authorId = authorId;
        this.title = title;
    }

    public String getid() {
        return id;
    }

    public void setid(String _id) {
        this.id = _id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
