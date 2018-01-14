package com.aptech.itblog.common;

public class CollectionLink {

    public static final String API = "/api";
    public static final String POSTS = "/posts";
    public static final String POSTS_TOP4 = "/posts/top4";
    public static final String POSTS_TYPE = "/posts/{type}";
    public static final String POSTS_ID = "/posts/{id}";

    public static final String USERS = "/users";
    public static final String USERS_ID = "/users/{id}";

    public static final String CATEGORIES = "/categories";
    public static final String CATEGORIES_ID = "/categories/{id}";

    public static final String ROLES = "/roles";
    public static final String ROLES_ID = "/roles/{id}";

    public static final String REGISTER = "/register";

    public static final String FORGOT_PASSWORD = "/forgot_password";
    public static final String FORGOT_PASSWORD_RESET = "/forgot_password/reset";

    public static final String GET_COMMENT = "/api/comment/{post_id}";
    public static final String CREATE_COMMENT = "/api/comment/{post_id}";
    public static final String EDIT_COMMENT = "/api/comment/{comment_id}";

}
