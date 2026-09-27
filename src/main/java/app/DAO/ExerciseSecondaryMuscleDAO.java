package app.DAO;

import app.entities.ExerciseSecondaryMuscle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class ExerciseSecondaryMuscleDAO {

    private final EntityManager em;

    public ExerciseSecondaryMuscleDAO(EntityManager em) {
        this.em = em;
    }


    public ExerciseSecondaryMuscle findBySecondaryMuscle(String secondary) {
        TypedQuery<ExerciseSecondaryMuscle> query = em.createQuery("SELECT s FROM ExerciseSecondaryMuscle s WHERE s.secondaryMuscle = :secondary"
                , ExerciseSecondaryMuscle.class);
        query.setParameter("secondary", secondary);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public ExerciseSecondaryMuscle save(ExerciseSecondaryMuscle secondary) {
        em.getTransaction().begin();
        em.persist(secondary);
        em.getTransaction().commit();
        return secondary;
    }

    public ExerciseSecondaryMuscle findOrCreate(String secondary) {
        ExerciseSecondaryMuscle found = findBySecondaryMuscle(secondary);
        if (found != null) {
            return found;
        }

        ExerciseSecondaryMuscle create = new ExerciseSecondaryMuscle();
        create.setSecondaryMuscle(secondary);
        return save(create);
    }

}

