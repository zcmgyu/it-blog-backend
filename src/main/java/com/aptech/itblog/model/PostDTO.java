package com.aptech.itblog.model;

public class PostDTO {
    private String id;

    private String title;

    private String shortContent;

    private String transliterated;

    private String image;

    private UserDTO user;

    public PostDTO() {}

    public PostDTO(String id, String title, String shortContent, String transliterated, String image, UserDTO user) {
        this.id = id;
        this.title = title;
        this.shortContent = shortContent;
        this.transliterated = transliterated;
        this.image = image;
        this.user = user;
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

    public String getShortContent() {
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}