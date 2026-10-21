package app;

import app.DAO.*;
import app.config.hibernate.HibernateConfig;
import app.controllers.ExerciseController;
import app.controllers.rslvController;
import app.exceptions.ExceptionMapper;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;

public class Main {


    public static void main(String[] args) {
        startServer(7070, HibernateConfig.getEntityManagerFactory());
    }

    public static Javalin startServer(int port, EntityManagerFactory emf) {
        ExerciseDAO exerciseDAO = new ExerciseDAO(emf);

        return Javalin.create(config -> {
            config.bundledPlugins.enableRouteOverview("api/v1/routes");
            ExceptionMapper.exceptionMapping(config);
            new rslvController(config, exerciseDAO).addRoutes();
            new ExerciseController(config, exerciseDAO).addRoutes();
        }).start(port);
    }

}
