package app;

import app.DAO.*;
import app.DTO.ExerciseDTO;
import app.config.hibernate.HibernateConfig;
import app.controllers.ExerciseController;
import app.entities.Exercise;
import app.mapper.ExerciseMapper;
import app.service.ExerciseAPI;
import app.service.LLMAPI;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.util.List;

public class Main {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public static void main(String[] args) throws IOException, InterruptedException {

        LLMAPI llmApi = new LLMAPI();
        // llmApi.askLlm();

        ExerciseDAO exerciseDAO = new ExerciseDAO(emf);

        /*
        ExerciseBodyPartDAO exerciseBodyPartDAO = new ExerciseBodyPartDAO(emf.createEntityManager());
        ExerciseTargetMuscleDAO exerciseTargetMuscleDAO = new ExerciseTargetMuscleDAO(emf.createEntityManager());
        ExerciseSecondaryMuscleDAO exerciseSecondaryMuscleDAO = new ExerciseSecondaryMuscleDAO(emf.createEntityManager());
        ExerciseEquipmentDAO exerciseEquipmentDAO = new ExerciseEquipmentDAO(emf.createEntityManager());
        ExerciseMapper exerciseMapper = new ExerciseMapper(exerciseBodyPartDAO,
                exerciseTargetMuscleDAO,
                exerciseSecondaryMuscleDAO,
                exerciseEquipmentDAO);

        ExerciseAPI exerciseAPI = new ExerciseAPI();
        List<ExerciseDTO> exerciseList = exerciseAPI.getAllExercises();
        System.out.println(exerciseList.size());

        List<Exercise> exercises = exerciseMapper.dtoToEntity(exerciseList);

        exerciseDAO.saveExerciseList(exercises);
        */

        Javalin app = Javalin.create(config -> {
            new ExerciseController(config, exerciseDAO).addRoutes();
        }).start(7070);



    }
}
