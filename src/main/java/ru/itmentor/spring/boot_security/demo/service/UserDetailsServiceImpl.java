package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.itmentor.spring.boot_security.demo.model.UserDetailsImpl;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Optional<User> user = userRepository.findByFirstName(username);
        // if (user.isEmpty())
        // throw new UsernameNotFoundException("Username not found: " + username);

        // return new UserDetailsImpl(user);

        // UserDetails user = User.withDefaultPasswordEncoder()
        // .username("user")
        // .password("user")
        // .roles("USER")
        // .build();
        // return user;
        return userRepository
                .findByNameWithRoles(username)
                .map(u -> {
                    System.out.println(u);
                    // u.setEnabled(true);
                    return new UserDetailsImpl(u);
                })
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " +
                        username));

    }

}
