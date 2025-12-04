package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.itmentor.spring.boot_security.demo.entity.Role;
import ru.itmentor.spring.boot_security.demo.entity.User;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User readByUsername(String userName) {
        return userRepository.findByFirstName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userName));
    }

    @Override
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> read(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> update(Long id, User newUserData) {
        newUserData.setEnabled(true);
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(newUserData.getFirstName());
                    existingUser.setLastName(newUserData.getLastName());
                    existingUser.setEnabled(newUserData.isEnabled());
                    existingUser.setEmail(newUserData.getEmail());

                    Set<Role> roles = newUserData.getRoles().stream()
                            .map(r -> roleRepository.findById(r.getId())
                                    .orElseThrow(() -> new RuntimeException("Role not found: " + r.getId())))
                            .collect(Collectors.toSet());
                    existingUser.setRoles(roles);
                    return userRepository.save(existingUser);
                });
    }

    @Override
    public boolean delete(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

}