package app.DAO;

import app.Config.HibernateTestConfig;
import app.entities.User;
import app.testutils.UserTestPopulator;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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
        User seed = seeded.get("User1");
        User fetched = userDAO.findById(seed.getId());
        assertThat(fetched.getId(), is(seed.getId()));
        assertThat(fetched.getName(), is(seed.getName()));

    }

    @Test
    void getAll() {
        List<User> all = userDAO.getAll();
        assertThat(all, hasSize(3));
        assertThat(all, containsInAnyOrder(seeded.get("User1"),
                seeded.get("User2"), seeded.get("User3")));
    }

    @Test
    void update() {
        User seed = seeded.get("User1");

        User updated = User.builder().id(seed.getId()).name("christian").age(24).email("christiank@gmail.com")
                .password("1234").phoneNumber("12345678").sex("Male")
                .height(171).currentWeight(71.4).signupDate(LocalDate.now()).build();

        User result = userDAO.update(updated);

        assertThat(result.getId(), is(seed.getId()));
        assertThat(result.getEmail(), is("christiank@gmail.com"));
        assertThat(result.getAge(), is(updated.getAge()));
    }

    @Test
    void delete() {
        User seed = seeded.get("User3");
        boolean deleted = userDAO.delete(seed.getId());

        assertThat(deleted, is(true));
    }
}