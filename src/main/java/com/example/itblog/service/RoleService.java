package com.example.itblog.service;

import com.example.itblog.model.Role;

public interface RoleService {

    void insert(Role role);

    void delete(Role role);

    void update(Role role);

    void getRoleById(long id);

    Role getByRoleName(String rolename);

    boolean isRoleExist(Role role);
}
