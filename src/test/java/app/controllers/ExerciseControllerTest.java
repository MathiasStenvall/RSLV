package app.controllers;

import app.Main;
import app.config.hibernate.HibernateConfig;
import app.testutils.ExerciseControllerTestPopulator;
import io.javalin.Javalin;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

class ExerciseControllerTest {

    private static EntityManagerFactory emf;
    private static Javalin app;
    private static RequestSpecification requestSpec;
    private List<Integer> ids;

    @BeforeAll
    static void startServer() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        app = Main.startServer(0, emf);

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
        ids = ExerciseControllerTestPopulator.populate(emf);
    }

    @AfterAll
    static void stopServer() {
        if (app != null) {
            app.stop();
        }
        HibernateConfig.closeEntityManagerFactoryForTest();
    }

    @Test
    void getAllExercises() {
        given()
                .spec(requestSpec)
                .when()
                .get("/exercise")
                .then()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    void getExerciseById() {
        int id = ids.get(0);

        given()
                .spec(requestSpec)
                .when()
                .get("/exercise/" + id)
                .then()
                .statusCode(200)
                .body("name", is("bench press"))
                .body("exerciseId", is("test01"));
    }

}