package com.aptech.itblog.repository;

import com.aptech.itblog.collection.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
    Role findByAuthority(String authority);
}
