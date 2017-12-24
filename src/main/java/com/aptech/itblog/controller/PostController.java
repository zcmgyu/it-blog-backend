package com.aptech.itblog.controller;

import com.aptech.itblog.collection.Post;
import com.aptech.itblog.collection.User;
import com.aptech.itblog.model.CommonResponseBody;
import com.aptech.itblog.model.CommonResult;
import com.aptech.itblog.model.Pagination;
import com.aptech.itblog.repository.UserRepository;
import com.aptech.itblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.aptech.itblog.common.CollectionLink.*;

@RestController
@RequestMapping(value = API)
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping(value = POSTS, headers = "Accept=application/json")
    public ResponseEntity<?> createPost(@Valid @RequestBody Post post) {
        // Set author id
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setAuthorId(user.get_id());

        // Set created date
        post.setCreateAt(new Date());

        // Create post
        boolean result = postService.createPost(post);


        if (result) {
            // Replace space with hyphen
            String transliterated = post.getTitle().replaceAll("\\s", "-");

            return new ResponseEntity<>(new CommonResponseBody("OK", 200,
                    new LinkedHashMap() {
                        {
                            put("message", "You created a post successfully");
                            put("post_id", post.get_id());
                            put("transliterated", transliterated);

                        }
                    }), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(value = POSTS, headers = "Accept=application/json")
    public ResponseEntity<?> getListPost(@RequestParam(required = false) Pagination pagination) {
        // Set default value page and size
        if (pagination == null) {
            pagination = new Pagination(0, 25);
        }
        List<Post> postList = postService.getListPost(pagination);
        return new ResponseEntity<>(new CommonResponseBody("OK", 200, new HashMap() {
            {
                put("post_list", postList);
            }
        }), HttpStatus.OK);
    }

    @GetMapping(value = POSTS_ID, headers = "Accept=application/json")
    public ResponseEntity<Post> getPostByID(@PathVariable("post_id") String post_id) {

        Post result = postService.getPost(post_id);

        if (null == result) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PutMapping(value = POSTS_ID, headers = "Accept=application/json")
    public ResponseEntity<?> updatePost(@PathVariable("post_id") String post_id, @RequestBody Post post) {

        post.set_id(post_id);
        boolean result = postService.updatePost(post);

        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = POSTS_ID, headers = "Accept=application/json")
    public ResponseEntity<?> deletePost(@PathVariable("post_id") String post_id) {

        boolean result = postService.deletePost(post_id);

        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
