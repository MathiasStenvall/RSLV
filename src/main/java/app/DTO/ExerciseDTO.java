package app.DTO;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ExerciseDTO {

    private String exerciseId;
    private String name;
    private String gifUrl;

    private Set<String> bodyParts;

    private Set<String> targetMuscles;

    private Set<String> secondaryMuscles;

    private Set<String> equipments;

    private List<String> instructions;

}
