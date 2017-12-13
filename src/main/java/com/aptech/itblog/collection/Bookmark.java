package com.aptech.itblog.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Document(collection = "Bookmark")
@JsonIgnoreProperties(value = {"createdAt"}, allowGetters = true)
public class Bookmark {

    @Id
    private String _id;

    private String userId;

    private List<String> listPostId;

    @NotEmpty
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createAt;

    @NotEmpty
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date modifiedAt;

    public Bookmark(String userId, List<String> listPostId, Date createAt, Date modifiedAt) {
        this.userId = userId;
        this.listPostId = listPostId;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }

    public String getId() {
        return _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getListPostId() {
        return listPostId;
    }

    public void setListPostId(List<String> listPostId) {
        this.listPostId = listPostId;
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
}
