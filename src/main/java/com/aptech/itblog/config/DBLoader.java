package com.aptech.itblog.config;

import com.aptech.itblog.collection.Follow;
import com.aptech.itblog.collection.User;
import com.aptech.itblog.repository.*;
import com.aptech.itblog.service.GAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DBLoader implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GAService gaService;

    @Autowired
    private FollowRepository followRepository;

    @Override
    public void run(String... strings) throws Exception {
//        roleRepository.save(new Role("USER"));
//        roleRepository.save(new Role("ADMIN"));
//        User user = new User();
//        user.setName("Administrator");
//        user.setPassword(new BCryptPasswordEncoder().encode("admin"));
//        user.setUsername("admin");
//        user.setEnabled(true);
//        user.setEmail("admin@itblog.com");
//
//        Role role = roleRepository.findByAuthority("ADMIN");
//        user.setRoles(new ArrayList() {
//            {
//                add(role.getId());
//            }
//        });
//        user.setAuthorities(new ArrayList() {
//            {
//                add(role);
//            }
//        });
//
//
//        userRepository.save(user);
//
//        List<Category> categoryList = new ArrayList() {
//            {
//                add(new Category("Development"));
//                add(new Category("Design"));
//                add(new Category("QA"));
//                add(new Category("Management"));
//            }
//        };
//        categoryRepository.save(categoryList);


//        List<String> ids = new ArrayList() {
//            {
//                add("5a5370ab9cbe8bc92ff5c25d");
//                add("5a5370ad9cbe8bc92ff5c25e");
//            }
//        };
//        List<Role> roleList = roleRepository.findAllByIdIn(ids);
//        System.out.println(roleList);

//        List<Post> posts = postRepository
//                .findTopByCategoryIdAndPublicPostInOrderByCreateAtDesc("QA", true);
//
//        System.out.println(posts);

//        AnalyticsReporting service = gaService.initializeAnalyticsReporting();
//        GetReportsResponse response = gaService.getReport(service);


//        User user1 = userRepository.findById("5a5370ad9cbe8bc92ff5c25f");
//        User user2 = userRepository.findById("5a53a7a29cbe8be541a66e2a");
//        User user3 = userRepository.findById("5a53b40f9cbe8bebf3adc11c");
//        User user4 = userRepository.findById("5a53c4699cbe8bebf3adc11d");
//
//        Follow follow1 = followRepository.save(new Follow(user1, Arrays.asList(new User[]{user2, user3})));
//        Follow follow2 = followRepository.save(new Follow(user4, Arrays.asList(new User[]{user3})));
//
//
//        followRepository.save(follow1);
//        followRepository.save(follow2);
//
//        List<Follow> followFound1 = followRepository.findByFollowingIn(Arrays.asList(new User[]{user3}));
//        List<Follow> followFound2 = followRepository.findByFollowingIn(Arrays.asList(new User[]{user2}));
//        List<Follow> followFound3 = followRepository.findByFollowing(user3);
//        List<Follow> followFound4 = followRepository.findByFollowing(user2);
    }
}
