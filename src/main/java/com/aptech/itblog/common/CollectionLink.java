package com.aptech.itblog.common;

public class CollectionLink {

    public static final String API = "/api";
    public static final String POSTS = "/posts";
    public static final String SEARCH = "/search";

    public static final String POSTS_TOP4_TYPE = "/posts/top4/{type}";
    public static final String POSTS_ID = "/posts/{id}";
    public static final String POSTS_ID_BOOKMARK = "/posts/{id}/bookmark";

    public static final String USERS = "/users";
    public static final String USERS_ID = "/users/{id}";
    public static final String USERS_ID_POSTS = "/users/{id}/posts";
    public static final String USERS_ID_FOLLOWING = "/users/{id}/following";
    public static final String USERS_ID_FOLLOWERS = "/users/{id}/followers";
    public static final String USERS_ID_FOLLOW = "/users/{id}/follow";
    public static final String USERS_SELF_BOOKMARK = "/users/self/bookmark";

    public static final String CATEGORIES = "/categories";
    public static final String CATEGORIES_ID = "/categories/{id}";

    public static final String FOLLOW_USER_ID = "/follow/{user_id}";

    public static final String ROLES = "/roles";
    public static final String ROLES_ID = "/roles/{id}";

    public static final String REGISTER = "/register";

    public static final String FORGOT_PASSWORD = "/forgot_password";
    public static final String FORGOT_PASSWORD_RESET = "/forgot_password/reset";

    public static final String GET_COMMENT = "/api/comment/{post_id}";
    public static final String CREATE_COMMENT = "/api/comment/{post_id}";
    public static final String EDIT_COMMENT = "/api/comment/{comment_id}";

}
