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
public class ExerciseData {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private int sets;
    private int reps;
    private double weight;

    @ElementCollection
    private List<String> notes;

}
