package com.aptech.itblog.service.ServiceImp;

import com.aptech.itblog.repository.RoleRepository;
import com.aptech.itblog.collection.Role;
import com.aptech.itblog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImp implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void insert(Role role) {

    }

    @Override
    public void delete(Role role) {

    }

    @Override
    public void update(Role role) {

    }

    @Override
    public void getRoleById(long id) {

    }

    @Override
    public Role getByRoleName(String rolename) {
        return null;
    }

    @Override
    public boolean isRoleExist(Role role) {
        return false;
    }
}
