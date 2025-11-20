package web.spring_boot_mvc.dao;

import java.util.List;
import web.spring_boot_mvc.model.User;

public interface UserDao {
    void create(User user);

    User read(Long id);

    void update(User user);

    void delete(Long id);

    List<User> findAll();
}
