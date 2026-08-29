package app.DAO;

import app.Config.HibernateTestConfig;
import app.entities.User;
import app.testutils.UserTestPopulator;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDAOTest {

    private final EntityManagerFactory emf = HibernateTestConfig.getEntityManagerFactory();

    private UserDAO userDAO;
    private Map<String, User> seeded;

    @BeforeEach
    void beforeEach(){
        seeded = UserTestPopulator.populate(emf);
        userDAO = new UserDAO(emf);
    }

    @AfterAll
    void shutdown(){
        emf.close();
    }



    @Test
    void save() {
        User newUser = User.builder().name("marshall").age(53).sex("Male").build();
        boolean created = userDAO.save(newUser);

        assertThat(String.valueOf(created), true);
    }

    @Test
    void findById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}