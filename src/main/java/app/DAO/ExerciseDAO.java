package app.DAO;

import app.entities.Exercise;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class ExerciseDAO {

    private final EntityManagerFactory emf;

    public ExerciseDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void saveExerciseList(List<Exercise> list) {
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            for (Exercise e: list){
                em.persist(e);
            }
            em.getTransaction().commit();
        }
    }

    public List<Exercise> getAllExercises(int page){
        int pageSize = 20;
        try (EntityManager em = emf.createEntityManager()){
            List<Exercise> exercises =  em.createQuery("SELECT e FROM Exercise e ORDER BY e.id", Exercise.class)
                    .setFirstResult((page-1) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
            for (Exercise e: exercises){
                int bpSize = e.getBodyParts().size();
                int tmSize = e.getTargetMuscles().size();
                int smSize = e.getSecondaryMuscles().size();
                int eSize = e.getEquipments().size();
            }
            //TODO add pagination ?
            return exercises;
        }
    }

    public Exercise getById(int id){
        try (EntityManager em = emf.createEntityManager()){
            Exercise found = em.find(Exercise.class, id);
            if (found != null){
                int bpSize = found.getBodyParts().size();
                int tmSize = found.getTargetMuscles().size();
                int smSize = found.getSecondaryMuscles().size();
                int eSize = found.getEquipments().size();

            }
            return found;
        }
    }

    public Exercise saveExercise(Exercise exercise){
        try (EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(exercise);
            em.getTransaction().commit();
            return exercise;
        }
    }

}
