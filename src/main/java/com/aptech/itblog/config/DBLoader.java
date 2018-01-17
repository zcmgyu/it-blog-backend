package com.aptech.itblog.config;

import com.aptech.itblog.repository.CategoryRepository;
import com.aptech.itblog.repository.PostRepository;
import com.aptech.itblog.repository.RoleRepository;
import com.aptech.itblog.repository.UserRepository;
import com.aptech.itblog.service.GAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

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

    }
}
