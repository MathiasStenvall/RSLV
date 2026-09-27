package app.service;

import app.DTO.APIResponse;
import app.DTO.ExerciseDTO;
import app.entities.Exercise;
import app.enums.BodyParts;
import app.enums.Equipment;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ExerciseAPI {

    private final String apiSearch = "https://oss.exercisedb.dev/api/v1/exercises?";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<ExerciseDTO> getExercisesWithBodyPartAndEquipment(BodyParts bodyParts, Equipment equipment) {
        List<ExerciseDTO> exercises;

        try {
            APIResponse apiResponse = objectMapper.readValue(new URL(apiSearch + "bodyParts=" + bodyParts.getUrlValue() + "&equipments=" + equipment.getUrlValue()), APIResponse.class);
            String cursor = apiResponse.getMeta().getNextCursor();
            boolean hasNext = apiResponse.getMeta().isHasNextPage();

            exercises = new ArrayList<>(apiResponse.getData());

            while (hasNext) {
                APIResponse response = objectMapper
                        .readValue(new URL(apiSearch + "after=" + cursor + "&bodyParts=" + bodyParts.getUrlValue() + "&equipments=" + equipment.getUrlValue()), APIResponse.class);
                cursor = response.getMeta().getNextCursor();
                hasNext = response.getMeta().isHasNextPage();
                exercises.addAll(response.getData());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return exercises;
    }

}
