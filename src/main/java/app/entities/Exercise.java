package app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Exercise {

    @Id
    private String exerciseId;
    private String name;
    private String gifUrl;

    @ElementCollection
    private List<String> bodyParts;

    @ElementCollection
    private List<String> targetMuscles;

    @ElementCollection
    private List<String> secondaryMuscles;

    @ElementCollection
    private List<String> equipments;

    @ElementCollection
    private List<String> instructions;

}
