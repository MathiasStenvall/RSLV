package app.testutils;

import app.entities.User;
import app.enums.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class RslvControllerTestPopulator {

    public static void populate (EntityManagerFactory emf){

        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();

            em.createQuery("SELECT u FROM User u", User.class).getResultList()
                    .forEach(em::remove);

            em.flush();

            User u1 = new User(
                    "Test001",
                    30,
                    "test001@user.com",
                    "testPasswordUser",
                    "12345678",
                    "female",
                    163,
                    62.6
            );

            em.persist(u1);

            User u2 = new User(
                    "Test002",
                    47,
                    "test002@admin.com",
                    "testPasswordAdmin",
                    "87654321",
                    "male",
                    183,
                    84.3
            );

            u2.setRole(Role.ADMIN);

            em.persist(u2);

            em.getTransaction().commit();

        }

    }

}
