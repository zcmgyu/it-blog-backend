package com.aptech.itblog.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Document(collection = "Follow")
@JsonIgnoreProperties(value = {"createdAt"}, allowGetters = true)
public class Follow {

    @Id
    private String id;

    @NotEmpty
    @DBRef
    private User user;

    @DBRef
    private List<User> following;

//    @DBRef
//    private List<User> followers;

    @NotEmpty
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createAt;

    @NotEmpty
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date modifiedAt;

    public Follow() {
    }

    public Follow(User user, List<User> following) {
        this.user = user;
        this.following = following;
    }

    //    public Follow(User user, List<User> following, List<User> followers) {
//        this.user = user;
//        this.following = following;
//        this.followers = followers;
//    }

    // GETTER & SETTER

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

//    public List<User> getFollowers() {
//        return followers;
//    }
//
//    public void setFollowers(List<User> followers) {
//        this.followers = followers;
//    }

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
