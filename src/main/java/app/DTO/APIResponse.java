package app.DTO;

import app.entities.Meta;
import lombok.Data;

import java.util.List;

@Data
public class APIResponse {

    private boolean success;
    private Meta meta;
    private List<ExerciseDTO> data;

}
