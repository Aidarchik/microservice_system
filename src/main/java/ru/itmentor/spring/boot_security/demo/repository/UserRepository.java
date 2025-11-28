package ru.itmentor.spring.boot_security.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.itmentor.spring.boot_security.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFirstName(String username);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.firstName = :name")
    Optional<User> findByNameWithRoles(@Param("name") String name);

}
