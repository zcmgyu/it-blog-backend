package com.aptech.itblog.controller;

import com.aptech.itblog.collection.Comment;
import com.aptech.itblog.collection.Follow;
import com.aptech.itblog.collection.Post;
import com.aptech.itblog.collection.User;
import com.aptech.itblog.exception.DataAccessException;
import com.aptech.itblog.model.CommonResponseBody;
import com.aptech.itblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import static com.aptech.itblog.common.CollectionLink.*;

@RestController
@RequestMapping(value = API)
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping(value = GET_COMMENT, headers = "Accept=application/json")
    public ResponseEntity<?> getAllComment() {

        try{
            commentService.getAllComment();
            return new ResponseEntity<>("", HttpStatus.OK);
        } catch (DataAccessException e)
        {
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(value = GET_COMMENT, headers = "Accept=application/json")
    public ResponseEntity<?> getAllReply() {

        try{
            commentService.getAllReply();
            return new ResponseEntity<>("", HttpStatus.OK);
        } catch (DataAccessException e)
        {
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(value = CREATE_COMMENT, headers = "Accept=application/json")
    public ResponseEntity<?> createComment(@Valid @RequestBody Comment comment) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        comment.setUserId(user.get_id());
        comment.setComment(true);

        // Set created date
        comment.setCreateAt(new Date());
        comment.setModifiedAt(new Date());

        // Add comment
        try{
            commentService.addComment(comment);
            return new ResponseEntity<>(new CommonResponseBody("OK", 200,
                    new LinkedHashMap() {
                        {
                            put("message", "You created a comment successfully");
                            put("comment_id", comment.get_id());
                        }
                    }), HttpStatus.OK);
        } catch (DataAccessException e)
        {
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(value = CREATE_REPLY, headers = "Accept=application/json")
    public ResponseEntity<?> createReply(@Valid @RequestBody Comment reply, @PathVariable("comment_id") String commentid) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        reply.setUserId(user.get_id());
        reply.setComment(false);

        Comment comment = commentService.getCommentById(commentid);
        List<String> replyId = new ArrayList<>();
        replyId.add(reply.get_id());

        // Set created date
        reply.setCreateAt(new Date());
        reply.setModifiedAt(new Date());

        // Add comment
        try{
            commentService.addComment(reply);
            commentService.updateComment(comment);
            return new ResponseEntity<>(new CommonResponseBody("OK", 200,
                    new LinkedHashMap() {
                        {
                            put("message", "You created a reply successfully");
                            put("comment_id", reply.get_id());
                        }
                    }), HttpStatus.OK);
        } catch (DataAccessException e)
        {
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping(value = EDIT_COMMENT, headers = "Accept=application/json")
    public ResponseEntity<?> editComment(@Valid @RequestBody Comment comment) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        comment.setUserId(user.get_id());

        // Set modified date
        comment.setModifiedAt(new Date());

        // Update comment
        try{
            commentService.updateComment(comment);
            return new ResponseEntity<>(new CommonResponseBody("OK", 200,
                    new LinkedHashMap() {
                        {
                            put("message", "You edit a comment successfully");
                            put("comment_id", comment.get_id());
                        }
                    }), HttpStatus.OK);
        } catch (DataAccessException e)
        {
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping(value = DELETE_COMMENT, headers = "Accept=application/json")
    public ResponseEntity<?> deleteComment(@PathVariable("comment_id") String commentid) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = commentService.getCommentById(commentid);
        comment.setCommentDelete(true);

        // Set modified date
        comment.setModifiedAt(new Date());

        // Update comment
        try{
            commentService.updateComment(comment);
            return new ResponseEntity<>(new CommonResponseBody("OK", 200,
                    new LinkedHashMap() {
                        {
                            put("message", "You delete a comment successfully");
                            put("comment_id", comment.get_id());
                        }
                    }), HttpStatus.OK);
        } catch (DataAccessException e)
        {
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
        }
    }
}
