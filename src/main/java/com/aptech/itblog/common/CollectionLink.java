package com.aptech.itblog.common;

/**
 * Provides some constants and utility methods to build a Link Header to be stored in the {@link HttpServletResponse} object
 */
public final class CollectionLink {

    public static final String CREATE_POST = "/api/posts";
    public static final String EDIT_POST = "/api/posts/{post_id}";
    public static final String GET_POST = "/api/posts/{post_id}";
    public static final String DELETE_POST = "/api/posts/{post_id}";

    public static final String API = "/api";
    public static final String POSTS = "/posts";
    public static final String POSTS_ID = "/posts/{post_id}";

    public static final String USERS = "/users";
    public static final String USERS_USER_ID = "/users/{user_id}";

    public static final String REGISTER = "/register";

    public static final String FORGOT_PASSWORD = "/forgot_password";
    public static final String FORGOT_PASSWORD_RESET = "/forgot_password/reset";

    public static final String GET_COMMENT = "/comment/{comment_id}";
    public static final String CREATE_COMMENT = "/comment/{comment_id}";
    public static final String CREATE_REPLY = "/comment/reply/{comment_id}";
    public static final String EDIT_COMMENT = "/comment/{comment_id}";
    public static final String DELETE_COMMENT = "/comment/{comment_id}";

    private CollectionLink() {
        throw new AssertionError();
    }

    //

    /**
     * Creates a Link Header to be stored in the {@link HttpServletResponse} to provide Discoverability features to the user
     *
     * @param uri
     *            the base uri
     * @param rel
     *            the relative path
     *
     * @return the complete url
     */
    public static String createLinkHeader(final String uri, final String rel) {
        return "<" + uri + ">; rel=\"" + rel + "\"";
    }
}
