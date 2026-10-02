package app.controllers;

import app.DAO.ExerciseDAO;
import app.entities.Exercise;
import app.enums.BodyParts;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.validation.ValidationException;

import java.util.Arrays;

public class ExerciseController {

    private JavalinConfig config;
    private ExerciseDAO dao;

    public ExerciseController(JavalinConfig config, ExerciseDAO dao) {
        this.config = config;
        this.dao = dao;
    }

    public void addRoutes() {
        config.routes.get("/api/v1/", ctx -> ctx.result("Welcome to RSLV API. \n" +
                "Feel free to use our endpoints to fetch exercises from our collection."));

        config.routes.get("/api/v1/exercise", ctx -> ctx.json(dao.getAllExercises()));

        config.routes.get("/api/v1/exercise/{id}", ctx -> {
            int id = ctx.pathParamAsClass("id", Integer.class)
                    .check(value -> value > 0, "Id must be a positive number").get();

            Exercise exercise = dao.getById(id);

            if (exercise == null) {
                ctx.result("No exercise found with id: " + id);
                throw new Exception();
            }
            ctx.json(exercise);
        });

        config.routes.post("/api/v1/exercise", ctx -> {
            try {
                Exercise exercise = ctx.bodyValidator(Exercise.class)
                        .check(e -> e.getName() != null
                                && !e.getName().isBlank(),"Please provide a name.")
                        .check(e -> e.getBodyParts() != null
                                && !e.getBodyParts().isEmpty(), "Please provide which body part the exercise trains.")
                        .check(e -> e.getBodyParts().stream().allMatch(bp -> Arrays.stream(BodyParts.values())
                                .anyMatch(b -> b.getUrlValue().equals(bp.getBodyPart()))), "Invalid body part.")
                        .check(e -> e.getEquipments() != null
                                && !e.getEquipments().isEmpty(), "Please provide the equipment needed to perform this exercise")
                        .check( e -> e.getInstructions() != null
                                && !e.getInstructions().isBlank(), "PLease provide instructions for the exercise").get();

                Exercise saved = dao.saveExercise(exercise);
                ctx.status(HttpStatus.CREATED);
                ctx.json(saved);

            } catch (ValidationException e) {
                ctx.status(HttpStatus.BAD_REQUEST).json(e.getErrors());
            }
        });
    }
}
