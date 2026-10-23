package app.controllers;

import app.DAO.ExerciseDAO;
import app.entities.Exercise;
import app.utils.Validator;
import io.javalin.config.JavalinConfig;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;
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
            int page = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);

            if (page <= 0) {
                throw new BadRequestResponse("Page must be a positive number");
            }

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
            Exercise exercise = Validator.validateExercise(ctx);

            Exercise saved = dao.saveExercise(exercise);
            ctx.status(HttpStatus.CREATED);
            ctx.json(saved);
        });

        config.routes.put("/api/v1/exercise/{id}", ctx -> {
            int id = ctx.pathParamAsClass("id", Integer.class)
                    .check(value -> value > 0, "Id must be a positive number").get();

            Exercise found = dao.getById(id);

            if (found == null) {
                throw new NotFoundResponse("Resource not found");
            }

            Exercise exercise = Validator.validateExercise(ctx);

            exercise.setId(id);

            Exercise updated = dao.updateExercise(exercise);

            ctx.json(updated);
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
}
