package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.entity.Role;

import java.util.Set;

public interface RoleService {
    Role createRole(Role role);

    Set<Role> findRoleByName(String[] roleName);

    Set<Role> findAllRoles();

    void deleteRole(Long id);
}
