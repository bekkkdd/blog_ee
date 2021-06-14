package com.bekdaulet.blog_project.services;

import com.bekdaulet.blog_project.models.Role;

public interface RoleService {
    Role getRoleByName(String name);
    Role addNewRole(String roleName);
}
