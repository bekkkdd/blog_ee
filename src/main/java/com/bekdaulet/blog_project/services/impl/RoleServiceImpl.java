package com.bekdaulet.blog_project.services.impl;

import com.bekdaulet.blog_project.models.Role;
import com.bekdaulet.blog_project.repositories.RoleRepository;
import com.bekdaulet.blog_project.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.getRoleByName(name);
    }

    @Override
    public Role addNewRole(String roleName) {
        if(roleName != null && !roleName.isBlank() && roleRepository.getRoleByName(roleName) == null) {
            return roleRepository.save(new Role(null, roleName.trim(), null));
        }
        return null;
    }
}
