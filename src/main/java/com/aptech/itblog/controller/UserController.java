package com.aptech.itblog.controller;

import com.aptech.itblog.collection.Post;
import com.aptech.itblog.collection.User;
import com.aptech.itblog.converter.PostConverter;
import com.aptech.itblog.exception.ConflictEmailException;
import com.aptech.itblog.model.CommonResponseBody;
import com.aptech.itblog.model.PostDTO;
import com.aptech.itblog.repository.RoleRepository;
import com.aptech.itblog.repository.UserRepository;
import com.aptech.itblog.service.PostService;
import com.aptech.itblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.Valid;
import java.util.*;

import static com.aptech.itblog.common.CollectionLink.*;

@RestController
@RequestMapping(value = API)
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserDetailsService userDetailsService; //Service which will do all data retrieval/manipulation work

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;


    @Autowired
    PostService postService;

    @Autowired
    PostConverter postConverter;

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        System.out.println(name + " parameter is missing");
        // Actual exception handling
    }

    @GetMapping(value = USERS)
    public ResponseEntity<?> getUserList(@RequestParam(required = false, defaultValue = "0") Integer page,
                                         @RequestParam(required = false, defaultValue = "25") Integer size) {
        // Create pageable
        Pageable pageable = new PageRequest(page, size);
        Page<User> userPage = userService.getAllUser(pageable);
        // Init a headers and add Content-Range
        HttpHeaders headers = new HttpHeaders() {
            {
                add("Access-Control-Expose-Headers", "Content-Range");
                add("Content-Range", String.valueOf(userPage.getTotalElements()));
            }
        };

        return new ResponseEntity<>(
                new CommonResponseBody("OK", 200,
                        new LinkedHashMap() {
                            {
                                put("data", userPage.getContent());
                            }
                        }), headers, HttpStatus.OK);

    }

    @PostMapping(value = USERS)
    @ResponseBody
    public ResponseEntity<?> register(@Valid @RequestBody User data) throws MissingServletRequestPartException {
        User user;
        try {
            user = (User) userDetailsService.loadUserByUsername(data.getUsername());

            if (user != null) {
                return new ResponseEntity<Object>(
                        new CommonResponseBody("RegisteredUser",
                                HttpStatus.CONFLICT.value(),
                                new HashMap() {
                                    {
                                        put("data", data.getId());
                                        put("message", "This username is already registered.");
                                    }
                                }),
                        HttpStatus.CONFLICT);
            }
        } catch (UsernameNotFoundException e) {
        }

        try {
            userService.loadUserByEmail(data.getEmail());
        } catch (ConflictEmailException e) {
            return new ResponseEntity<Object>(
                    new CommonResponseBody("RegisteredEmail",
                            HttpStatus.CONFLICT.value(),
                            new HashMap() {
                                {
                                    put("data", data.getId());
                                    put("message", "This email is already registered.");
                                }
                            }),
                    HttpStatus.CONFLICT);
        }

        // Save user to DB
        userService.registerUser(data);

        return new ResponseEntity<Object>(new CommonResponseBody("OK", HttpStatus.OK.value(),
                new HashMap() {
                    {
                        put("data", data);
                        put("message", "You've been successfully registered.");
                    }
                }),
                HttpStatus.OK);
    }

    @GetMapping(value = USERS_ID)
    public ResponseEntity<?> getCurrentUser(@PathVariable("id") String userId) {
        // Define user
        User user = userService.getUser(userId);


        return new ResponseEntity<Object>(new CommonResponseBody("OK", 200, new LinkedHashMap() {
            {
                put("data", user);
            }
        }), HttpStatus.OK);
    }

    @PutMapping(value = USERS_ID)
    public ResponseEntity<?> updateUser(@PathVariable("id") String userId, @RequestBody User user) {
        User currentUser = userService.updateUser(userId, user);

        return new ResponseEntity<Object>(new CommonResponseBody("OK", 200, new LinkedHashMap() {
            {
                put("data", currentUser);
            }
        }), HttpStatus.OK);
    }


    @DeleteMapping(value = USERS_ID)
    public ResponseEntity<?> deleteUser(@PathVariable("id") String userId) {
        userService.deleteUser(userId);

        return new ResponseEntity<Object>(new CommonResponseBody("OK", 200, new LinkedHashMap() {
            {
                put("data", userId);
            }
        }), HttpStatus.OK);
    }


    @GetMapping(value = USERS_ID_POSTS, headers = "Accept=application/json")
    public ResponseEntity<?> getPostListByUser(
            @PathVariable(value = "id") String authorId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "25") Integer size) {
        // Create pageable
        Pageable pageable = new PageRequest(page, size);
        Page<Post> postPage = postService.getPagePostByAuthorId(authorId, pageable);

        Page<PostDTO> postDTOPage = postPage.map(post -> postConverter.convertToDto(post));

        HttpHeaders headers = new HttpHeaders() {
            {
                add("Access-Control-Expose-Headers", "Content-Range");
                add("Content-Range", String.valueOf(postDTOPage.getTotalElements()));
            }
        };

        return new ResponseEntity<>(new CommonResponseBody("OK", 200, new LinkedHashMap() {
            {
                put("data", postDTOPage.getContent());
            }
        }), headers, HttpStatus.OK);
    }



}