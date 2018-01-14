package com.aptech.itblog.repository;

import com.aptech.itblog.collection.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoleRepository extends MongoRepository<Role, String> {
    Role findByAuthority(String authority);

    List<Role> findAllById(List<String> ids);

    List<Role> findAllByIdIn(List<String> ids);
}
