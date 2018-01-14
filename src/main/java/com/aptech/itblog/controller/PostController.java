package com.aptech.itblog.controller;

import com.aptech.itblog.collection.Post;
import com.aptech.itblog.collection.User;
import com.aptech.itblog.converter.PostConverter;
import com.aptech.itblog.model.PostDTO;
import com.aptech.itblog.model.CommonResponseBody;
import com.aptech.itblog.repository.CategoryRepository;
import com.aptech.itblog.service.PostService;
import com.aptech.itblog.utils.StringUtils;
import org.modelmapper.ModelMapper;
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
    private PostService postService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostConverter postConverter;

    /**
     * Entity to DTO
     * http://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application
     *
     */
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = POSTS, headers = "Accept=application/json")
    public ResponseEntity<?> createPost(@Valid @RequestBody Post post) {
        // Set author id
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setAuthorId(user.getId());

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

    @GetMapping(value = POSTS_TOP4, headers = "Accept=application/json")
    public ResponseEntity<?> getTop4ByCategory() {
        LinkedHashMap<String, List<Post>> postMap = postService.getTop4ByCategory();

        LinkedHashMap<String, List<PostDTO>> postDTOMap = new LinkedHashMap<>();



        for (Map.Entry<String, List<Post>> post: postMap.entrySet()) {
            // convert to DTO
            List<PostDTO> postDTOList = post.getValue()
                    .stream()
                    .map(p -> postConverter.convertToDto(p))
                    .collect(Collectors.toList());
            postDTOMap.put(post.getKey(), postDTOList);
        }

        return new ResponseEntity<>(new CommonResponseBody("OK", 200, new LinkedHashMap() {
            {
                put("data", postDTOMap);
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

        if (!post.getAuthorId().equals(currentUserId)) {
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




}
