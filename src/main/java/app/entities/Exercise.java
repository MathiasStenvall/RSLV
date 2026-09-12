package app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
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

    @ElementCollection
    private List<String> instructions;

}
