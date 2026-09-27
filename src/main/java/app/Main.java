package app;

import app.DAO.ExerciseBodyPartDAO;
import app.DAO.ExerciseEquipmentDAO;
import app.DAO.ExerciseSecondaryMuscleDAO;
import app.DAO.ExerciseTargetMuscleDAO;
import app.DTO.ExerciseDTO;
import app.config.hibernate.HibernateConfig;
import app.entities.Exercise;
import app.enums.BodyParts;
import app.enums.Equipment;
import app.mapper.ExerciseMapper;
import app.service.ExerciseAPI;
import app.service.LLMAPI;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.util.List;

public class Main {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public static void main(String[] args) {

        LLMAPI llmApi = new LLMAPI();
        // llmApi.askLlm();

        ExerciseAPI exerciseAPI = new ExerciseAPI();
        List<ExerciseDTO> backLeverageMachine = exerciseAPI.getExercisesWithBodyPartAndEquipment(BodyParts.BACK, Equipment.LEVERAGE_MACHINE);
        System.out.println(backLeverageMachine.size());

        ExerciseBodyPartDAO exerciseBodyPartDAO = new ExerciseBodyPartDAO(emf.createEntityManager());
        ExerciseTargetMuscleDAO exerciseTargetMuscleDAO = new ExerciseTargetMuscleDAO(emf.createEntityManager());
        ExerciseSecondaryMuscleDAO exerciseSecondaryMuscleDAO = new ExerciseSecondaryMuscleDAO(emf.createEntityManager());
        ExerciseEquipmentDAO exerciseEquipmentDAO = new ExerciseEquipmentDAO(emf.createEntityManager());
        ExerciseMapper exerciseMapper = new ExerciseMapper(exerciseBodyPartDAO,
                exerciseTargetMuscleDAO,
                exerciseSecondaryMuscleDAO,
                exerciseEquipmentDAO);

        List<Exercise> exercises = exerciseMapper.dtoToEntity(backLeverageMachine);
        System.out.println(exercises.size());

        emf.close();
    }
}
