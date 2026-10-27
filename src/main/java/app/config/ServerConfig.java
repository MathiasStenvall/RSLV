package app.config;

import app.DAO.ExerciseDAO;
import app.DAO.UserDAO;
import app.controllers.ExerciseController;
import app.controllers.rslvController;
import app.exceptions.ExceptionMapper;
import app.service.JWTAuthenticator;
import app.service.JWTService;
import app.utils.Utils;
import app.service.Validator;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;

public class ServerConfig {

    public static Javalin setupServer(int port, EntityManagerFactory emf) {
        String encodedSecret;

        if (System.getenv("DEPLOYED") != null) {
            encodedSecret = System.getenv("JWT_SECRET");
        } else {
            encodedSecret = Utils.getPropertyValue("JWT_SECRET", "config.properties");
        }
        return startServer(port, emf, encodedSecret);

    }

    public static Javalin startServer(int port, EntityManagerFactory emf, String encodedSecret){
        ExerciseDAO exerciseDAO = new ExerciseDAO(emf);
        UserDAO userDAO = new UserDAO(emf);
        Validator validator = new Validator();

        JWTService jwtService = new JWTService(encodedSecret);
        JWTAuthenticator jwtAuthenticator = new JWTAuthenticator(jwtService);

        return Javalin.create(config -> {
            config.bundledPlugins.enableRouteOverview("api/v1/routes");
            ExceptionMapper.exceptionMapping(config);
            new rslvController(config, userDAO, exerciseDAO, validator, jwtService).addRoutes();
            new ExerciseController(config, exerciseDAO, validator, jwtAuthenticator).addRoutes();
        }).start(port);
    }

}
