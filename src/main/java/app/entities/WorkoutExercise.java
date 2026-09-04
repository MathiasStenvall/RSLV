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
public class WorkoutExercise {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    @ManyToMany (mappedBy = "exercises")
    private List<Workout> workouts;

    @ManyToOne
    @JoinColumn (name = "exercise_id")
    private Exercise exercise;

    @OneToOne
    @JoinColumn (name = "exercise_data_id")
    private ExerciseData exerciseData;

}
