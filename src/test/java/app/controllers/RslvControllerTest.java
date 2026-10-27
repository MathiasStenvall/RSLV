package app.controllers;

import app.DTO.UserLoginDTO;
import app.DTO.UserRegistrationDTO;
import app.config.ServerConfig;
import app.config.hibernate.HibernateConfig;
import app.testutils.RslvControllerTestPopulator;
import com.nimbusds.jose.JOSEException;
import io.javalin.Javalin;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class RslvControllerTest {

    private static EntityManagerFactory emf;
    private static Javalin app;
    private static RequestSpecification requestSpec;
    private List<Integer> ids;
    private static final String TEST_SECRET = Base64.getEncoder().encodeToString(new byte[32]);

    @BeforeAll
    static void startServer() throws JOSEException {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        app = ServerConfig.startServer(0, emf, TEST_SECRET);

        requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(app.port())
                .setBasePath("/api/v1")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    @BeforeEach
    void populateDB() {
        ids = RslvControllerTestPopulator.populate(emf);
    }

    @AfterAll
    static void stopServer() {
        if (app != null) {
            app.stop();
        }
        HibernateConfig.closeEntityManagerFactoryForTest();
    }

    @Test
    void register() {

        UserRegistrationDTO u3 = new UserRegistrationDTO(
                "Test003",
                33,
                "test003@user.com",
                "testPasswordUser",
                "12345678",
                "female",
                153,
                52.9
        );

        given()
                .spec(requestSpec)
                .body(u3)
                .when()
                .post("/register")
                .then()
                .statusCode(201)
                .body("message", is("User registered successfully!"));

        UserRegistrationDTO sameEmail = new UserRegistrationDTO(
                "Test003",
                33,
                "test001@user.com",
                "testPasswordUser",
                "12345678",
                "female",
                153,
                52.9
        );

        given()
                .spec(requestSpec)
                .body(sameEmail)
                .when()
                .post("/register")
                .then()
                .statusCode(409);

        UserRegistrationDTO noEmail = new UserRegistrationDTO(
                "Test003",
                33,
                "",
                "testPasswordUser",
                "12345678",
                "female",
                153,
                52.9
        );

        given()
                .spec(requestSpec)
                .body(noEmail)
                .when()
                .post("/register")
                .then()
                .statusCode(400);

    }

    @Test
    void login() {
        UserLoginDTO u1 = new UserLoginDTO(
                "test001@user.com",
                "testPasswordUser"
        );

        given()
                .spec(requestSpec)
                .body(u1)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());

        UserLoginDTO invalidPassword = new UserLoginDTO(
                "test002@admin.com",
                "testPasswordFail"
        );

        given()
                .spec(requestSpec)
                .body(invalidPassword)
                .when()
                .post("/login")
                .then()
                .statusCode(401);

    }

}