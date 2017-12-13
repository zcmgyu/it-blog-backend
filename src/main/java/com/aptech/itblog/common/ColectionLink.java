package com.aptech.itblog.common;

public class ColectionLink {

    public static final String CREATE_POST = "/api/posts";
    public static final String EDIT_POST = "/api/posts/{post_id}";
    public static final String GET_POST = "/api/posts/{post_id}";
    public static final String DELETE_POST = "/api/posts/{post_id}";

    public static final String GET_COMMENT = "/api/comment/{post_id}";
    public static final String CREATE_COMMENT = "/api/comment/{post_id}";
    public static final String EDIT_COMMENT = "/api/comment/{comment_id}";

}
