package app;

import app.DAO.IDAO;
import app.DAO.UserDAO;
import app.config.hibernate.HibernateConfig;
import app.entities.User;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;

public class Main {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public static void main(String[] args) {

        IDAO<User, Integer> userDAO = new UserDAO(emf);

        User u1 = User.builder().name("christian").age(23).email("ckenter@gmail.com")
                .password("1234").phoneNumber("12345678").sex("Male")
                .height(171).currentWeight(71.4).signupDate(LocalDate.now()).build();

        System.out.println(u1);

        emf.close();
    }
}
