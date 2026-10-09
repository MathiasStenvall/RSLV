package app.testutils;

import app.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public final class UserTestPopulator {

    private UserTestPopulator() {
    }

    public static Map<String, User> populate(EntityManagerFactory emf) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            User u1 = new User("christian", 23, "user", "ckenter@gmail.com", "1234",
                    "12345678", "male", 171, 71.4);

            User u2 = new User("nicoline", 25, "user", "nico@gmail.com", "1234",
                    "12345678", "female", 168, 64.7);

            User u3 = new User("rosa", 23, "user", "rosa@gmail.com", "1234",
                    "12345678", "female", 151, 56.1);

            try {
                em.createNativeQuery("TRUNCATE TABLE users RESTART IDENTITY CASCADE").executeUpdate();
                em.persist(u1);
                em.persist(u2);
                em.persist(u3);
                em.flush();
            } catch (PersistenceException e) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                throw e;
            }
            em.getTransaction().commit();

            Map<String, User> seeded = new LinkedHashMap<>();
            seeded.put("User1", u1);
            seeded.put("User2", u2);
            seeded.put("User3", u3);
            return seeded;
        }
    }
}
