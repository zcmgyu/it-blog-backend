package com.aptech.itblog.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Document(collection = "Follow")
@JsonIgnoreProperties(value = {"createdAt"}, allowGetters = true)
public class Follow {

    @Id
    private String _id;

    @NotEmpty
    private String userId;

    private List<String> followed;

    private List<String> following;

    @NotEmpty
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createAt;

    @NotEmpty
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date modifiedAt;

    public Follow() {
    }

    public Follow(String userId, List<String> followed, List<String> following, Date createAt, Date modifiedAt) {
        this.userId = userId;
        this.followed = followed;
        this.following = following;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }

    public String getId() {
        return _id;
    }

    public String getUserId() {
        return userId;
    }

    public List<String> getFollowed() {
        return followed;
    }

    public void setFollowed(List<String> followed) {
        this.followed = followed;
    }

    public List<String> getFollowing() {
        return following;
    }

    public void setFollowing(List<String> following) {
        this.following = following;
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
