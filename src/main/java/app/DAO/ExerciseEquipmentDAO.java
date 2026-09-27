package app.DAO;

import app.entities.ExerciseEquipment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class ExerciseEquipmentDAO {

    private final EntityManager em;

    public ExerciseEquipmentDAO (EntityManager em) {
        this.em = em;
    }


    public ExerciseEquipment findByEquipment(String equipment) {
        TypedQuery<ExerciseEquipment> query = em.createQuery("SELECT e FROM ExerciseEquipment e WHERE e.equipment = :equipment"
                , ExerciseEquipment.class);
        query.setParameter("equipment", equipment);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public ExerciseEquipment save(ExerciseEquipment equipment) {
        em.getTransaction().begin();
        em.persist(equipment);
        em.getTransaction().commit();
        return equipment;
    }

    public ExerciseEquipment findOrCreate(String equipment) {
        ExerciseEquipment found = findByEquipment(equipment);
        if (found != null) {
            return found;
        }

        ExerciseEquipment create = new ExerciseEquipment();
        create.setEquipment(equipment);
        return save(create);
    }

}
