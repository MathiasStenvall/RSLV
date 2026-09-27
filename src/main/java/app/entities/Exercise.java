package app.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Exercise {

    @Id
    private String exerciseId;
    private String name;
    private String gifUrl;

    @ManyToMany
    private Set<ExerciseBodyPart> bodyParts;

    @ManyToMany
    private Set<ExerciseTargetMuscle> targetMuscles;

    @ManyToMany
    private Set<ExerciseSecondaryMuscle> secondaryMuscles;

    @ManyToMany
    private Set<ExerciseEquipment> equipments;

    private String instructions;

}
