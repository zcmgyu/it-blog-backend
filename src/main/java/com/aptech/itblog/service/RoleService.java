package com.aptech.itblog.service;

import com.aptech.itblog.collection.Role;

public interface RoleService {

    void insert(Role role);

    void delete(Role role);

    void update(Role role);

    void getRoleById(long id);

    Role getByRoleName(String rolename);

    boolean isRoleExist(Role role);
}
