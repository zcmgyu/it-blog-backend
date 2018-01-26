package com.aptech.itblog.controller;

import com.aptech.itblog.collection.Post;
import com.aptech.itblog.collection.User;
import com.aptech.itblog.converter.PostConverter;
import com.aptech.itblog.model.PostByCategory;
import com.aptech.itblog.model.PostByCategoryDTO;
import com.aptech.itblog.model.CommonResponseBody;
import com.aptech.itblog.service.BookmarkService;
import com.aptech.itblog.service.LoveService;
import com.aptech.itblog.service.PostService;
import com.aptech.itblog.utils.StringUtils;
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
import java.util.stream.Collectors;

import static com.aptech.itblog.common.CollectionLink.*;

@RestController
@RequestMapping(value = API)
public class PostController {
    @Autowired
    public PostService postService;

    @Autowired
    public PostConverter postConverter;

    @Autowired
    public BookmarkService bookmarkService;

    @Autowired
    public LoveService loveService;

    /**
     * Entity to DTO
     * http://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application
     */
    @PostMapping(value = POSTS, headers = "Accept=application/json")
    public ResponseEntity<?> createPost(@Valid @RequestBody Post post) {
        // Create post
        Post createdPost = postService.createPost(post);


        if (createdPost != null) {
            // String without accent
            String withoutAccent = StringUtils.removeAccent(createdPost.getTitle());

            // Replace space with hyphen
            String transliterated = withoutAccent.replaceAll("\\s", "-");

            return new ResponseEntity<>(new CommonResponseBody("OK", 200,
                    new LinkedHashMap() {
                        {
                            put("message", "You created a post successfully");
                            put("post_id", createdPost.getId());
                            put("transliterated", transliterated);

                        }
                    }), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(value = POSTS_TOP4_TYPE, headers = "Accept=application/json")
    public ResponseEntity<?> getTop4ByCategory(@PathVariable(value = "type") String type) {
        List<PostByCategory> postByCategories;

        switch (type) {
            case "trend":
                postByCategories = postService.getTop4TrendPostByCategory();
                break;
            default:
                postByCategories = postService.getTop4LatestPostByCategory();
                break;
        }

        // Convert to DTO
        List<PostByCategoryDTO> postByCategoryDTO = postByCategories
                .stream()
                .map(postByCategory -> postConverter.convertToPostByCategoryDTO(postByCategory))
                .collect(Collectors.toList());

        return new ResponseEntity<>(new CommonResponseBody("OK", 200, new LinkedHashMap() {
            {
                put("data", postByCategoryDTO);
            }
        }), HttpStatus.OK);
    }

    @GetMapping(value = POSTS, headers = "Accept=application/json")
    public ResponseEntity<?> getListPost(@RequestParam(required = false, defaultValue = "0") Integer page,
                                         @RequestParam(required = false, defaultValue = "25") Integer size,
                                         @RequestParam(required = false, defaultValue = "latest") String type) {
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

        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(new CommonResponseBody("OK", 200, result), HttpStatus.OK);
        }
    }


    @PutMapping(value = POSTS_ID, headers = "Accept=application/json")
    public ResponseEntity<?> updatePost(@PathVariable("id") String postId, @RequestBody Post post) {
        // Get current user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUserId = user.getId();

        if (!post.getAuthor().getId().equals(currentUserId)) {
            return new ResponseEntity<>(new CommonResponseBody("Forbidden", 403,
                    new LinkedHashMap() {
                        {
                            put("message", "You can only edit posts that you made.");

                        }
                    }), HttpStatus.FORBIDDEN);
        }


        Post updatedPost = postService.updatePost(postId, post);

        // String without accent
        String withoutAccent = StringUtils.removeAccent(updatedPost.getTitle());
        // Replace space with hyphen
        String transliterated = withoutAccent.replaceAll("\\s", "-");

        if (updatedPost != null) {
            return new ResponseEntity<>(new CommonResponseBody("OK", 200,
                    new LinkedHashMap() {
                        {
                            put("message", "You updated a post successfully");
                            put("post_id", updatedPost.getId());
                            put("transliterated", transliterated);

                        }
                    }), HttpStatus.OK);
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

    @PutMapping(value = POSTS_ID_BOOKMARK)
    public ResponseEntity<?> toggleBookmark(
            @PathVariable(value = "id") String targetPostId
    ) {
        String message = bookmarkService.toggleBookmark(targetPostId);

        return new ResponseEntity<>(new CommonResponseBody("OK", 200, new LinkedHashMap() {
            {
                put("message", message);
            }
        }), HttpStatus.OK);
    }

    @PutMapping(value = POSTS_ID_LOVE)
    public ResponseEntity<?> toggleLove(
            @PathVariable(value = "id") String targetPostId
    ) {
        String message = loveService.toggleBookmark(targetPostId);

        return new ResponseEntity<>(new CommonResponseBody("OK", 200, new LinkedHashMap() {
            {
                put("message", message);
            }
        }), HttpStatus.OK);
    }


}
