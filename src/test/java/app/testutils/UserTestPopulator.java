package app.testutils;

import app.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;


public final class UserTestPopulator {

    private UserTestPopulator(){}

    public static Map<String, User> populate(EntityManagerFactory emf) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            LocalDate testDate = LocalDate.of(2026, 8, 29);

            User u1 = User.builder().name("christian").age(23).email("ckenter@gmail.com")
                    .password("1234").phoneNumber("12345678").sex("Male")
                    .height(171).currentWeight(71.4).signupDate(testDate).build();
            User u2 = User.builder().name("nicoline").age(25).email("nico@gmail.com")
                    .password("1234").phoneNumber("12345678").sex("Female")
                    .height(168).currentWeight(64.7).signupDate(testDate.plusDays(2)).build();
            User u3 = User.builder().name("rosa").age(23).email("rosa@gmail.com")
                    .password("1234").phoneNumber("12345678").sex("Female")
                    .height(151).currentWeight(56.1).signupDate(testDate.plusDays(5)).build();

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
