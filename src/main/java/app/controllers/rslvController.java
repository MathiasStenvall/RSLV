package app.controllers;

import app.DAO.ExerciseDAO;
import app.utils.RequestCounter;
import io.javalin.config.JavalinConfig;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class rslvController {

    private final JavalinConfig config;
    private final ExerciseDAO dao;
    private final ExecutorService executor = Executors.newFixedThreadPool(3);

    public rslvController(JavalinConfig config, ExerciseDAO dao) {
        this.config = config;
        this.dao = dao;
    }

    public void addRoutes() {
        config.routes.before(ctx -> RequestCounter.increment());

        config.routes.get("/api/v1/counter", ctx -> ctx.result("This API has received "
                + RequestCounter.getCounter() + " requests since its last reset (including this one!)."));

        config.routes.get("/api/v1/stats", ctx -> {
            try {
                Future<Long> exerciseAmount = executor.submit(dao::getExerciseAmount);
                Future<Long> equipmentAmount = executor.submit(dao::getEquipmentAmount);
                Future<Long> bodyPartAmount = executor.submit(dao::getBodyPartAmount);
                Future<Long> targetMuscleAmount = executor.submit(dao::getTargetMuscleAmount);

                long exercises = exerciseAmount.get();
                long equipment = equipmentAmount.get();
                long bodyParts = bodyPartAmount.get();
                long targetMuscle = targetMuscleAmount.get();

                ctx.json(Map.of(
                        "exercises", exercises,
                        "equipment", equipment,
                        "bodyParts", bodyParts,
                        "targetMuscles", targetMuscle
                ));
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                throw new RuntimeException("Could not retrieve data from database.", e);
            } catch (ExecutionException e){
                throw new RuntimeException("Could not retrieve data from database.", e);
            }
        });

        config.routes.get("/", ctx -> ctx.redirect("/api/v1"));

        config.routes.get("/api/v1/", ctx -> ctx.result("Welcome to RSLV API. \n" +
                "Feel free to use our endpoints to fetch exercises from our collection."));
    }

}
