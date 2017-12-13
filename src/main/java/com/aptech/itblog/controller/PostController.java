package com.aptech.itblog.controller;

import com.aptech.itblog.collection.Post;
import com.aptech.itblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.aptech.itblog.common.ColectionLink.*;

@RestController
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService _postService) {
        this.postService = _postService;
    }

    @PostMapping(value = CREATE_POST, headers = "Accept=application/json")
    public ResponseEntity<?> createPost(@RequestBody Post post) {

        boolean result = postService.createPost(post);
        HttpHeaders httpHeaders = new HttpHeaders();

        if(result){
            return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = GET_POST ,headers = "Accept=application/json")
    public ResponseEntity<Post> getPostByID(@PathVariable("post_id") String post_id) {

        Post result = postService.getPost(post_id);

        if(null == result){
            return new ResponseEntity<> (HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<> (result, HttpStatus.OK);
        }
    }

    @PutMapping(value = EDIT_POST, headers = "Accept=application/json")
    public ResponseEntity<?> updatePost(@PathVariable("post_id") String post_id ,@RequestBody Post post) {

        post.set_id(post_id);
        boolean result = postService.updatePost(post);

        if(result){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = DELETE_POST, headers = "Accept=application/json")
    public ResponseEntity<?> deletePost(@PathVariable("post_id") String post_id) {

        boolean result = postService.deletePost(post_id);

        if(result){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
