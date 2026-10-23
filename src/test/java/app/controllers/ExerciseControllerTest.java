package app.controllers;

import app.Main;
import app.config.hibernate.HibernateConfig;
import app.entities.User;
import app.enums.Role;
import app.service.JWTService;
import app.testutils.ExerciseControllerTestPopulator;
import app.utils.Utils;
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

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

class ExerciseControllerTest {

    private static EntityManagerFactory emf;
    private static Javalin app;
    private static RequestSpecification requestSpec;
    private List<Integer> ids;
    private static JWTService jwtService;
    private static String adminToken;

    @BeforeAll
    static void startServer() throws JOSEException {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        app = Main.startServer(0, emf);

        User admin = new User();
        admin.setId(1);
        admin.setRole(Role.ADMIN);

        String encodedSecret = Utils.getPropertyValue("JWT_SECRET", "config.properties");

        jwtService = new JWTService(encodedSecret);
        adminToken = jwtService.generateToken(admin);

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

    @Test
    void postExercise() {
        String json = """
                {
                "exerciseId": "test03",
                "name": "incline bench press",
                "instructions": "press the bar upward",
                "bodyParts": [
                {"bodyPart": "chest"}
                ],
                "equipments": [
                {"equipment": "barbell"}
                ],
                "targetMuscles": [],
                "secondaryMuscles": []
                }
                """;

        given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + adminToken)
                .body(json)
                .when()
                .post("/exercise")
                .then()
                .statusCode(201)
                .body("name", is("incline bench press"))
                .body("exerciseId", is("test03"));
    }

    @Test
    void putExercise() {
        int id = ids.get(0);

        String json = """
                {
                "exerciseId": "updatedTest01",
                "name": "updated bench press",
                "instructions": "Lower the bar to your lower chest and press the bar upward",
                "bodyParts": [
                {"bodyPart": "chest"}
                ],
                "equipments": [
                {"equipment": "barbell"}
                ],
                "targetMuscles": [],
                "secondaryMuscles": []
                }
                """;

        given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + adminToken)
                .body(json)
                .when()
                .put("/exercise/" + id)
                .then()
                .statusCode(200)
                .body("name", is("updated bench press"))
                .body("exerciseId", is("updatedTest01"))
                .body("id", is(id));
    }

    @Test
    void deleteExercise() {
        int id = ids.get(0);

        given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .delete("/exercise/" + id)
                .then()
                .statusCode(204);

        given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + adminToken)
                .when()
                .get("/exercise/" + id)
                .then()
                .statusCode(404);
    }

}