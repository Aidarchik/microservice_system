package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.itmentor.spring.boot_security.demo.dao.UserDao;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public void create(User user) {
        userDao.create(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User read(Long id) {
        return userDao.read(id);
    }

    @Transactional
    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Transactional
    @Override
    public void deleteRole(Long id) {
        userDao.deleteRole(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> findAllRoles() {
        return userDao.findAllRoles();
    }

    @Transactional
    @Override
    public void createRole(Role role) {
        userDao.createRole(role);
    }
}