package com.aptech.itblog.config;

import com.aptech.itblog.collection.Category;
import com.aptech.itblog.collection.User;
import com.aptech.itblog.repository.CategoryRepository;
import com.aptech.itblog.repository.RoleRepository;
import com.aptech.itblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DBLoader implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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
//        user.setRoles(new ArrayList() {
//            {
//                add(roleRepository.findByName("ADMIN"));
//            }
//        });


//        userRepository.save(user);

//        List<Category> categoryList = new ArrayList() {
//            {
//                add(new Category("Development"));
//                add(new Category("Design"));
//                add(new Category("QA"));
//                add(new Category("Management"));
//            }
//        };
//
//        categoryRepository.save(categoryList);
    }
}
