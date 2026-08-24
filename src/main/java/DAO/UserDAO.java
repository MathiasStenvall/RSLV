package DAO;

import config.hibernate.HibernateConfig;
import entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class UserDAO {

    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public void saveUser (User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    public User findById (int id) {
        EntityManager em = emf.createEntityManager();
        User foundUser = em.find(User.class, id);
        em.close();
        return foundUser;
    }

    public void updateUser (User user){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteUser (int id){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User user = findById(id);
        if (user != null){
            em.remove(user);
        }
        em.getTransaction().commit();
        em.close();
    }

    public void close() {
        emf.close();
    }

}
