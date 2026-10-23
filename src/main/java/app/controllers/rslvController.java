package app.controllers;

import app.DAO.ExerciseDAO;
import app.DAO.UserDAO;
import app.DTO.UserLoginDTO;
import app.DTO.UserRegistrationDTO;
import app.entities.User;
import app.exceptions.UserAlreadyExistsException;
import app.service.JWTService;
import app.utils.RequestCounter;
import app.utils.Validator;
import io.javalin.config.JavalinConfig;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.HttpStatus;
import io.javalin.http.InternalServerErrorResponse;
import io.javalin.http.UnauthorizedResponse;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class rslvController {

    private final JavalinConfig config;
    private final UserDAO userDAO;
    private final ExerciseDAO exerciseDAO;
    private final ExecutorService executor = Executors.newFixedThreadPool(3);
    private final Validator validator;
    private final JWTService jwtService;

    public rslvController(JavalinConfig config, UserDAO userDAO
            , ExerciseDAO exerciseDAO, Validator validator, JWTService jwtService) {
        this.config = config;
        this.userDAO = userDAO;
        this.exerciseDAO = exerciseDAO;
        this.validator = validator;
        this.jwtService = jwtService;
    }

    public void addRoutes() {
        config.routes.before(ctx -> RequestCounter.increment());

        config.routes.post("/api/v1/register", ctx -> {
            UserRegistrationDTO dto = ctx.bodyAsClass(UserRegistrationDTO.class);
            if (!validator.validateEmailAndPassword(dto.email(), dto.password())) {
                throw new BadRequestResponse("Email and password are required.");
            }

            if (userDAO.findByEmail(dto.email()) != null) {
                throw new UserAlreadyExistsException("User with " + dto.email() + " already exists");
            }

            User user = new User(
                    dto.name(),
                    dto.age(),
                    dto.email(),
                    dto.password(),
                    dto.phoneNumber(),
                    dto.sex(),
                    dto.height(),
                    dto.currentWeight()
            );

            if (!userDAO.save(user)) {
                throw new InternalServerErrorResponse("Registration failed.");
            }

            ctx.status(HttpStatus.CREATED);
            ctx.json(Map.of("message", "User registered succesfully!"));
        });

        config.routes.post("/api/v1/login", ctx -> {
            UserLoginDTO dto = ctx.bodyAsClass(UserLoginDTO.class);

            if (!validator.validateEmailAndPassword(dto.email(), dto.password())) {
                throw new BadRequestResponse("Email and password are required.");
            }

           User user = userDAO.findByEmail(dto.email());

           if (user == null || !user.checkPassword(dto.password())){
               throw new UnauthorizedResponse("Invalid email or password.");
           }

           ctx.status(HttpStatus.OK);
           String token = jwtService.generateToken(user);
           ctx.json(Map.of("token", token));
        });

        config.routes.get("/api/v1/counter", ctx -> ctx.result("This API has received "
                + RequestCounter.getCounter() + " requests since its last reset (including this one!)."));

        config.routes.get("/api/v1/stats", ctx -> {
            try {
                Future<Long> exerciseAmount = executor.submit(exerciseDAO::getExerciseAmount);
                Future<Long> equipmentAmount = executor.submit(exerciseDAO::getEquipmentAmount);
                Future<Long> bodyPartAmount = executor.submit(exerciseDAO::getBodyPartAmount);
                Future<Long> targetMuscleAmount = executor.submit(exerciseDAO::getTargetMuscleAmount);

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
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Could not retrieve data from database.", e);
            } catch (ExecutionException e) {
                throw new RuntimeException("Could not retrieve data from database.", e);
            }
        });

        config.routes.get("/", ctx -> ctx.redirect("/api/v1"));

        config.routes.get("/api/v1/", ctx -> ctx.result("""
                Welcome to RSLV API.\s
                Feel free to use our endpoints to fetch exercises from our collection.\s
                Visit /routes to see all available endpoints.\s
                All data was fetched from AscendAPI."""));
    }

}
