package app.DAO;

import app.entities.ExerciseTargetMuscle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class ExerciseTargetMuscleDAO {

    private final EntityManager em;

    public ExerciseTargetMuscleDAO(EntityManager em){
        this.em = em;
    }


    public ExerciseTargetMuscle findByTargetMuscle (String targetMuscle){
        TypedQuery<ExerciseTargetMuscle> query = em.createQuery("SELECT t FROM ExerciseTargetMuscle t WHERE t.targetMuscle = :targetMuscle"
                , ExerciseTargetMuscle.class);
        query.setParameter("targetMuscle", targetMuscle);
        try {
            return query.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

    public ExerciseTargetMuscle save (ExerciseTargetMuscle targetMuscle){
        em.getTransaction().begin();
        em.persist(targetMuscle);
        em.getTransaction().commit();
        return targetMuscle;
    }

    public ExerciseTargetMuscle findOrCreate (String targetMuscle){
        ExerciseTargetMuscle found = findByTargetMuscle(targetMuscle);
        if (found != null){
            return found;
        }

        ExerciseTargetMuscle create = new ExerciseTargetMuscle();
        create.setTargetMuscle(targetMuscle);
        return save(create);
    }

}
