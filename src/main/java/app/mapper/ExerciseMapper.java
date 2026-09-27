package app.mapper;

import app.DAO.ExerciseBodyPartDAO;
import app.DAO.ExerciseEquipmentDAO;
import app.DAO.ExerciseSecondaryMuscleDAO;
import app.DAO.ExerciseTargetMuscleDAO;
import app.DTO.ExerciseDTO;
import app.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ExerciseMapper {

    private final ExerciseBodyPartDAO exerciseBodyPartDAO;
    private final ExerciseTargetMuscleDAO exerciseTargetMuscleDAO;
    private final ExerciseSecondaryMuscleDAO exerciseSecondaryMuscleDAO;
    private final ExerciseEquipmentDAO exerciseEquipmentDAO;

    public ExerciseMapper(
            ExerciseBodyPartDAO exerciseBodyPartDAO,
            ExerciseTargetMuscleDAO exerciseTargetMuscleDAO,
            ExerciseSecondaryMuscleDAO exerciseSecondaryMuscleDAO,
            ExerciseEquipmentDAO exerciseEquipmentDAO
    ) {
        this.exerciseBodyPartDAO = exerciseBodyPartDAO;
        this.exerciseTargetMuscleDAO = exerciseTargetMuscleDAO;
        this.exerciseSecondaryMuscleDAO = exerciseSecondaryMuscleDAO;
        this.exerciseEquipmentDAO = exerciseEquipmentDAO;
    }

    public List<Exercise> dtoToEntity(List<ExerciseDTO> dto) {
        List<Exercise> exercises = new ArrayList<>();
        for (ExerciseDTO eDTO : dto) {
            Exercise exercise = new Exercise();

            exercise.setExerciseId(eDTO.getExerciseId());
            exercise.setName(eDTO.getName());
            exercise.setGifUrl(eDTO.getGifUrl());

            Set<ExerciseBodyPart> bodyParts = eDTO.getBodyParts().stream()
                    .map(exerciseBodyPartDAO::findOrCreate).collect(Collectors.toSet());
            exercise.setBodyParts(bodyParts);

            Set<ExerciseTargetMuscle> targetMuscles = eDTO.getTargetMuscles().stream()
                    .map(exerciseTargetMuscleDAO::findOrCreate).collect(Collectors.toSet());
            exercise.setTargetMuscles(targetMuscles);

            Set<ExerciseSecondaryMuscle> secondaryMuscles = eDTO.getSecondaryMuscles().stream()
                    .map(exerciseSecondaryMuscleDAO::findOrCreate).collect(Collectors.toSet());
            exercise.setSecondaryMuscles(secondaryMuscles);

            Set<ExerciseEquipment> equipment = eDTO.getEquipments().stream()
                    .map(exerciseEquipmentDAO::findOrCreate).collect(Collectors.toSet());
            exercise.setEquipments(equipment);

            List<String> steps = eDTO.getInstructions();
            String instructions = String.join("\n", steps);
            exercise.setInstructions(instructions);

            exercises.add(exercise);
        }
        return exercises;

    }
}
