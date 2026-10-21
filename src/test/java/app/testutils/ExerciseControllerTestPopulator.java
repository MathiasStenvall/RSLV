package app.testutils;

import app.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ExerciseControllerTestPopulator {

    public static List<Integer> populate(EntityManagerFactory emf) {

        List<Integer> ids = new ArrayList<>();

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            em.createQuery("SELECT e FROM Exercise e", Exercise.class).getResultList()
                    .forEach(em::remove);

            em.flush();

            em.createQuery("DELETE FROM ExerciseBodyPart").executeUpdate();
            em.createQuery("DELETE FROM ExerciseEquipment").executeUpdate();
            em.createQuery("DELETE FROM ExerciseSecondaryMuscle").executeUpdate();
            em.createQuery("DELETE FROM ExerciseTargetMuscle").executeUpdate();

            ExerciseBodyPart bp1 = new ExerciseBodyPart();
            bp1.setBodyPart("chest");
            em.persist(bp1);

            ExerciseEquipment eq1 = new ExerciseEquipment();
            eq1.setEquipment("barbell");
            em.persist(eq1);

            ExerciseSecondaryMuscle sm1 = new ExerciseSecondaryMuscle();
            sm1.setSecondaryMuscle("triceps");
            em.persist(sm1);

            ExerciseTargetMuscle tm1 = new ExerciseTargetMuscle();
            tm1.setTargetMuscle("pectorals");
            em.persist(tm1);

            Exercise benchPress = new Exercise();
            benchPress.setExerciseId("test01");
            benchPress.setName("bench press");
            benchPress.setInstructions("lower and press the bar upward.");
            benchPress.setBodyParts(Set.of(bp1));
            benchPress.setEquipments(Set.of(eq1));
            benchPress.setSecondaryMuscles(Set.of(sm1));
            benchPress.setTargetMuscles(Set.of(tm1));

            em.persist(benchPress);
            int id1 = benchPress.getId();
            ids.add(id1);

            ExerciseBodyPart bp2 = new ExerciseBodyPart();
            bp2.setBodyPart("back");
            em.persist(bp2);

            ExerciseEquipment eq2 = new ExerciseEquipment();
            eq2.setEquipment("leverage machine");
            em.persist(eq2);

            ExerciseSecondaryMuscle sm2 = new ExerciseSecondaryMuscle();
            sm2.setSecondaryMuscle("biceps");
            em.persist(sm2);

            ExerciseTargetMuscle tm2 = new ExerciseTargetMuscle();
            tm2.setTargetMuscle("rhomboids");
            em.persist(tm2);

            Exercise row = new Exercise();
            row.setExerciseId("test02");
            row.setName("seated row");
            row.setInstructions("Sit on the pad and pull towards your chest.");
            row.setBodyParts(Set.of(bp2));
            row.setEquipments(Set.of(eq2));
            row.setSecondaryMuscles(Set.of(sm2));
            row.setTargetMuscles(Set.of(tm2));

            em.persist(row);
            int id2 = row.getId();
            ids.add(id2);

            em.getTransaction().commit();

            return ids;
        }
    }

}
