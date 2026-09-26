package app;

import app.config.hibernate.HibernateConfig;
import app.entities.Exercise;
import app.enums.BodyParts;
import app.enums.Equipment;
import app.service.ExerciseAPI;
import app.service.LLMAPI;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.util.List;

public class Main {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public static void main(String[] args) throws IOException, InterruptedException {

        LLMAPI llmApi = new LLMAPI();
        llmApi.askLlm();

        ExerciseAPI exerciseAPI = new ExerciseAPI();
        List<Exercise> chestCable = exerciseAPI.getExercisesWithBodyPartAndEquipment(BodyParts.CHEST, Equipment.CABLE);
        System.out.println(chestCable.size());


        emf.close();
    }
}
