package ru.itmentor.spring.boot_security.demo.dao;

import java.util.List;

import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;

public interface UserDao {
    void create(User user);

    void createRole(Role role);

    User read(Long id);

    void update(User user);

    void delete(Long id);

    void deleteRole(Long id);

    List<User> findAll();

    List<Role> findAllRoles();
}
