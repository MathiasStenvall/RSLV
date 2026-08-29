package app.DAO;

import app.config.hibernate.HibernateConfig;
import app.entities.User;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UserDAO implements IDAO<User, Integer> {

    private final EntityManagerFactory emf;

    public UserDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public boolean save(User user) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            try {
                em.persist(user);
                em.getTransaction().commit();
                return true;
            } catch (Exception e) {
                em.getTransaction().isActive();
                em.getTransaction().rollback();
                return false;
            }
        }
    }

    public User findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        User foundUser = em.find(User.class, id);
        em.close();
        return foundUser;
    }

    public List<User> getAll() {
        try (EntityManager em = emf.createEntityManager()){
            try {
                TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
                return query.getResultList();
            } catch (PersistenceException e){
                throw new ApiException(500, "Get users failed: " + e.getMessage());
            }
        }
    }

    public User update(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User updatedUser = em.merge(user);
        em.getTransaction().commit();
        em.close();
        return updatedUser;
    }

    public boolean delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                em.getTransaction().begin();
                User user = findById(id);
                if (user != null) {
                    em.remove(user);
                    em.getTransaction().commit();
                    return true;
                }
            } catch (Exception e) {
                em.getTransaction().isActive();
                em.getTransaction().rollback();
                throw e;
            }
        }
        return false;
    }

    public void close() {
        emf.close();
    }

}
