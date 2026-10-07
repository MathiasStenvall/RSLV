package app.controllers;

import app.DAO.ExerciseDAO;
import app.entities.Exercise;
import app.utils.RequestCounter;
import app.enums.BodyParts;
import io.javalin.config.JavalinConfig;
import io.javalin.http.BadRequestResponse;
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

        config.routes.before(ctx -> RequestCounter.increment());

        config.routes.get("/api/v1/counter", ctx -> ctx.result("This api has received "
                + RequestCounter.getCounter() + " requests since its last reset (including this one!)."));

        config.routes.get("/api/v1/", ctx -> ctx.result("Welcome to RSLV API. \n" +
                "Feel free to use our endpoints to fetch exercises from our collection."));

        config.routes.get("/api/v1/exercise", ctx -> {
            int page = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
            List<Exercise> result = dao.getAllExercises(page);
            if (result.isEmpty()){
                throw new NotFoundResponse("Resource not found");
            }
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
                                && !e.getInstructions().isBlank(), "Please provide instructions for the exercise").get();

                Exercise saved = dao.saveExercise(exercise);
                ctx.status(HttpStatus.CREATED);
                ctx.json(saved);

            } catch (ValidationException e) {
                throw new BadRequestResponse("Bad request");
            }
        });
    }
}
