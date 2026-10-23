package app;

import app.DAO.*;
import app.config.hibernate.HibernateConfig;
import app.controllers.ExerciseController;
import app.controllers.rslvController;
import app.entities.User;
import app.exceptions.ExceptionMapper;
import app.utils.Validator;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;

public class Main {


    public static void main(String[] args) {
        startServer(7070, HibernateConfig.getEntityManagerFactory());
    }

    public static Javalin startServer(int port, EntityManagerFactory emf) {
        ExerciseDAO exerciseDAO = new ExerciseDAO(emf);
        UserDAO userDAO = new UserDAO(emf);
        Validator validator = new Validator();

        return Javalin.create(config -> {
            config.bundledPlugins.enableRouteOverview("api/v1/routes");
            ExceptionMapper.exceptionMapping(config);
            new rslvController(config, userDAO, exerciseDAO, validator).addRoutes();
            new ExerciseController(config, exerciseDAO, validator).addRoutes();
        }).start(port);
    }

}
