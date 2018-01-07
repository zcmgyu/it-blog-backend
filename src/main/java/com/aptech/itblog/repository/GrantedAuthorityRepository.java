package com.aptech.itblog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface GrantedAuthorityRepository extends MongoRepository<GrantedAuthority, String> {
    GrantedAuthority findGrantedAuthorityByAuthority(String authority);
}
