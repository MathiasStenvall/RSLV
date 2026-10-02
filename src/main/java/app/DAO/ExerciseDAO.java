package app.DAO;

import app.entities.Exercise;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class ExerciseDAO {

    private EntityManagerFactory emf;

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

}
