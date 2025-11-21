package ru.itmentor.spring.boot_security.demo.dao;

import java.util.List;

import ru.itmentor.spring.boot_security.demo.model.User;

public interface UserDao {
    void create(User user);

    User read(Long id);

    void update(User user);

    void delete(Long id);

    List<User> findAll();
}
