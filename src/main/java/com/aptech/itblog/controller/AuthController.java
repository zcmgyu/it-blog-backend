package com.aptech.itblog.controller;

import com.aptech.itblog.collection.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import static com.aptech.itblog.common.CollectionLink.API;

@RestController
@RequestMapping(API)
public class AuthController {
    @GetMapping(value = "/auth/roles")
    @ResponseBody
    public ResponseEntity<?> register() throws MissingServletRequestPartException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<Object>(auth.getAuthorities(), HttpStatus.OK);
    }

//    @Autowired
//    UserDetailsService userDetailsService; //Service which will do all data retrieval/manipulation work
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    RoleRepository roleRepository;
//
//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    public void handleMissingParams(MissingServletRequestParameterException ex) {
//        String name = ex.getParameterName();
//        System.out.println(name + " parameter is missing");
//        // Actual exception handling
//    }

//    @RequestMapping(value = REGISTER, method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseEntity<?> register(@Valid @RequestBody User data) throws MissingServletRequestPartException {
//        User user;
//        try {
//            user = (User) userDetailsService.loadUserByUsername(data.getUsername());
//
//            if (user != null) {
//                return new ResponseEntity<Object>(
//                        new CommonResponseBody("RegisteredUser",
//                                HttpStatus.CONFLICT.value(),
//                                new HashMap() {
//                                    {
//                                        put("message", "This username is already registered.");
//                                    }
//                                }),
//                        HttpStatus.CONFLICT);
//            }
//        } catch (UsernameNotFoundException e) {
//        }
//
//        try {
//            userService.loadUserByEmail(data.getEmail());
//        } catch (ConflictEmailException e) {
//            return new ResponseEntity<Object>(
//                    new CommonResponseBody("RegisteredEmail",
//                            HttpStatus.CONFLICT.value(),
//                            new HashMap() {
//                                {
//                                    put("message", "This email is already registered.");
//                                }
//                            }),
//                    HttpStatus.CONFLICT);
//        }
//
//        // Encode password
//        BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
//        String hashedPassword = passEncoder.encode(data.getPassword());
//        data.setPassword(hashedPassword);
//
//        // Setting roles for user
//        List<Role> roles = new ArrayList() {
//            {
//                add(roleRepository.findByName("USER"));
//            }
//        };
//        data.setRoles(roles);
//
//        // Set default enabled
//        data.setEnabled(true);
//
//        // Set created date
//        data.setCreateAt(new Date());
//
//        // Save user to DB
//        userService.addUser(data);
//
//        return new ResponseEntity<Object>(new CommonResponseBody("OK", HttpStatus.OK.value(),
//                new HashMap() {
//                    {
//                        put("message", "You've been successfully registered.");
//                    }
//                }),
//                HttpStatus.OK);
//    }
}
