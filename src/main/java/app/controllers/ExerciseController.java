package app.controllers;

import app.DAO.ExerciseDAO;
import app.entities.Exercise;
import app.enums.BodyParts;
import app.enums.Equipment;
import io.javalin.config.JavalinConfig;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;
import io.javalin.validation.ValidationException;

import java.util.Arrays;
import java.util.List;


public class ExerciseController {

    private final JavalinConfig config;
    private final ExerciseDAO dao;

    public ExerciseController(JavalinConfig config, ExerciseDAO dao) {
        this.config = config;
        this.dao = dao;
    }

    public void addRoutes() {

        config.routes.get("/api/v1/exercise", ctx -> {
            int page = ctx.queryParamAsClass("page", Integer.class)
                    .check(value -> value > 0, "page must be a positive number")
                    .getOrDefault(1);
            List<Exercise> result = dao.getAllExercises(page);

            ctx.json(result);
        });

        config.routes.get("/api/v1/exercise/{id}", ctx -> {
            int id = ctx.pathParamAsClass("id", Integer.class)
                    .check(value -> value > 0, "Id must be a positive number").get();

            Exercise exercise = dao.getById(id);

            if (exercise == null) {
                throw new NotFoundResponse("Resource not found");
            }
            ctx.json(exercise);
        });

        config.routes.post("/api/v1/exercise", ctx -> {
            try {
                Exercise exercise = validateExercise(ctx);

                Exercise saved = dao.saveExercise(exercise);
                ctx.status(HttpStatus.CREATED);
                ctx.json(saved);

            } catch (ValidationException e) {
                throw new BadRequestResponse("Bad request");
            }
        });

        config.routes.put("/api/v1/exercise/{id}", ctx -> {
            int id = ctx.pathParamAsClass("id", Integer.class)
                    .check(value -> value > 0, "Id must be a positive number").get();

            Exercise found = dao.getById(id);

            if (found == null) {
                throw new NotFoundResponse("Resource not found");
            }

            try {
                Exercise exercise = validateExercise(ctx);

                exercise.setId(id);

                Exercise updated = dao.updateExercise(exercise);

                ctx.json(updated);
            } catch (ValidationException e) {
                throw new BadRequestResponse("Bad Request");
            }
        });

        config.routes.delete("/api/v1/exercise/{id}", ctx -> {
            int id = ctx.pathParamAsClass("id", Integer.class)
                    .check(value -> value > 0, "Id must be a positive number").get();

            boolean deleted = dao.deleteExercise(id);

            if (!deleted) {
                throw new NotFoundResponse("Exercise not found");
            }

            ctx.status(HttpStatus.NO_CONTENT);
        });

    }

    private Exercise validateExercise(Context ctx) {
        return ctx.bodyValidator(Exercise.class)
                .check(e -> e.getName() != null
                        && !e.getName().isBlank(), "Please provide a name.")
                .check(e -> e.getBodyParts() != null
                        && !e.getBodyParts().isEmpty(), "Please provide which body part the exercise trains.")
                .check(e -> e.getBodyParts() != null
                        && e.getBodyParts().stream().allMatch(bp -> bp != null && Arrays.stream(BodyParts.values())
                        .anyMatch(b -> b.getValue().equals(bp.getBodyPart()))), "Invalid body part.")
                .check(e -> e.getEquipments() != null
                        && !e.getEquipments().isEmpty(), "Please provide the equipment needed to perform this exercise")
                .check(e -> e.getEquipments() != null
                        && e.getEquipments().stream().allMatch(eq -> eq != null && Arrays.stream(Equipment.values())
                        .anyMatch(equ -> equ.getValue().equals(eq.getEquipment()))), "Invalid equipment")
                .check(e -> e.getInstructions() != null
                        && !e.getInstructions().isBlank(), "Please provide instructions for the exercise").get();
    }
}
