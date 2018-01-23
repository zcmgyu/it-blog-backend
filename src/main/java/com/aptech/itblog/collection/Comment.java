package com.aptech.itblog.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Document(collection = "Comments")
@JsonIgnoreProperties(value = {"createdAt"}, allowGetters = true)
public class Comment {

    @Id
    private String _id;

    @NotEmpty
    private String userId;

    @NotEmpty
    private String postId;

    private boolean isComment;

    private boolean isCommentDelete;

    @NotEmpty
    private String content;

    private List<String> replyId;

    @NotEmpty
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createAt;

    @NotEmpty
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date modifiedAt;

    public Comment() {
    }

    public Comment(String userId, String postId, boolean isComment, String content, List<String> replyId) {
        this.userId = userId;
        this.postId = postId;
        this.isComment = isComment;
        this.content = content;
        this.replyId = replyId;
        this.isCommentDelete = false;
    }

    public boolean isCommentDelete() {
        return isCommentDelete;
    }

    public void setCommentDelete(boolean commentDelete) {
        isCommentDelete = commentDelete;
    }

    public boolean isComment() {
        return isComment;
    }

    public void setComment(boolean comment) {
        isComment = comment;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getReplyId() {
        return replyId;
    }

    public void setReplyId(List<String> replyId) {
        this.replyId = replyId;
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