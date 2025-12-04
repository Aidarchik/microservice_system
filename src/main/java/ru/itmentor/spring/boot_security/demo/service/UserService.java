package ru.itmentor.spring.boot_security.demo.service;

import java.util.List;
import java.util.Optional;

import ru.itmentor.spring.boot_security.demo.entity.User;

public interface UserService {
    User create(User user);

    Optional<User> read(Long id);

    User readByUsername(String userName);

    Optional<User> update(Long id, User newUserData);

    boolean delete(Long id);

    List<User> findAll();

}