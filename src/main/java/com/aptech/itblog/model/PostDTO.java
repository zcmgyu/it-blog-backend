package com.aptech.itblog.model;

import com.aptech.itblog.collection.User;

import java.util.Date;
import java.util.List;

public class PostDTO {
    private String id;

    private String title;

    private String rawContent;

    private String transliterated;

    private String image;

    private UserDTO author;

    private List<UserDTO> favorite;

    private Date createAt;

    private Date modifiedAt;

    public PostDTO() {}

    public PostDTO(String title, String rawContent, String transliterated, String image, UserDTO author, List<UserDTO> favorite) {
        this.title = title;
        this.rawContent = rawContent;
        this.transliterated = transliterated;
        this.image = image;
        this.author = author;
        this.favorite = favorite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }

    public String getTransliterated() {
        return transliterated;
    }

    public void setTransliterated(String transliterated) {
        this.transliterated = transliterated;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public List<UserDTO> getFavorite() {
        return favorite;
    }

    public void setFavorite(List<UserDTO> favorite) {
        this.favorite = favorite;
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
