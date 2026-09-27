package app.DAO;

import app.entities.ExerciseBodyPart;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class ExerciseBodyPartDAO {

    private final EntityManager em;

    public ExerciseBodyPartDAO (EntityManager em){
        this.em = em;
    }

    public ExerciseBodyPart findByBodyPart(String bodyPart){
        TypedQuery<ExerciseBodyPart> query = em.createQuery("SELECT b FROM ExerciseBodyPart b WHERE b.bodyPart = :bodyPart"
        , ExerciseBodyPart.class);
        query.setParameter("bodyPart", bodyPart);
        try {
            return query.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

    public ExerciseBodyPart save (ExerciseBodyPart bodyPart){
        em.getTransaction().begin();
        em.persist(bodyPart);
        em.getTransaction().commit();
        return bodyPart;
    }

    public ExerciseBodyPart findOrCreate (String bodyPart){
        ExerciseBodyPart found = findByBodyPart(bodyPart);
        if (found != null){
            return found;
        }

        ExerciseBodyPart create = new ExerciseBodyPart();
        create.setBodyPart(bodyPart);
        return save(create);
    }

}
