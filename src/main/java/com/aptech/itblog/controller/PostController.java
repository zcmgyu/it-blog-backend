package com.aptech.itblog.controller;

import com.aptech.itblog.collection.Post;
import com.aptech.itblog.collection.User;
import com.aptech.itblog.model.AdminQuery;
import com.aptech.itblog.model.CommonResponseBody;
import com.aptech.itblog.model.Pagination;
import com.aptech.itblog.service.PostService;
import com.aptech.itblog.utils.ParseUtils;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

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
        post.setAuthorId(user.getId());

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
                            put("post_id", post.getId());
                            put("transliterated", transliterated);

                        }
                    }), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(value = POSTS, headers = "Accept=application/json")
    public ResponseEntity<?> getListPost(@RequestParam(required = false, defaultValue = "0") Integer page,
                                         @RequestParam(required = false, defaultValue = "25") Integer size) {
        // Create pageable
        Pageable pageable = new PageRequest(page, size);
        Page<Post> postPage = postService.getPagePost(pageable);

        HttpHeaders headers = new HttpHeaders() {
            {
                add("Access-Control-Expose-Headers", "Content-Range");
                add("Content-Range", String.valueOf(postPage.getTotalElements()));
            }
        };

        return new ResponseEntity<>(new CommonResponseBody("OK", 200, new LinkedHashMap() {
            {
                put("data", postPage.getContent());
            }
        }), headers, HttpStatus.OK);
    }

    @GetMapping(value = POSTS_ID, headers = "Accept=application/json")
    public ResponseEntity<Post> getPostByID(@PathVariable("id") String postId) {

        Post result = postService.getPost(postId);

        if (null == result) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {

            return new ResponseEntity(new CommonResponseBody("OK", 200, result), HttpStatus.OK);
        }
    }

//    , @RequestParam("range") List range, @RequestParam("sort") List sort

    @GetMapping(value = "/posts/test")
    public ResponseEntity<Post> getPostTest(@RequestParam Map<String, String> query) {

        System.out.println(query.toString());

        Map filter = new JSONObject(query.get("filter")).toMap();
        List sort = new JSONArray(query.get("sort")).toList();
        List range = new JSONArray(query.get("range")).toList();

//        sort=["title","ASC"]&range=[0, 24]&filter={"title":"bar"}
//        List<String> sort = ParseUtils.parseList(query.get("sort"));
//        List<String> range = ParseUtils.parseList(query.get("range"));
//        HashMap<String, String> filter = ParseUtils.parseMap(query.get("filter"));

        List<Post> postList = postService.getListPost();
//        return new ResponseEntity(new CommonResponseBody("OK", 200, postList), HttpStatus.OK);
        return new ResponseEntity(postList, HttpStatus.OK);
    }

    @PutMapping(value = POSTS_ID, headers = "Accept=application/json")
    public ResponseEntity<?> updatePost(@PathVariable("id") String postId, @RequestBody Post post) {

        post.setId(postId);
        boolean result = postService.updatePost(post);

        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = POSTS_ID, headers = "Accept=application/json")
    public ResponseEntity<?> deletePost(@PathVariable("id") String postId) {

        boolean result = postService.deletePost(postId);

        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
