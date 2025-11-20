
package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(User user) {
        entityManager.persist(user);
    }

    @Override
    public User read(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(Long id) {
        entityManager.createQuery("DELETE FROM User user WHERE user.id= :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT user FROM User user", User.class).getResultList();
    }
}
