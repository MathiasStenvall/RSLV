package app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int age;

    private String email;

    private String password;

    private String phoneNumber;

    private String sex;

    private int height;

    private double currentWeight;

    private LocalDate signupDate;

    @OneToMany (mappedBy = "user")
    private List<WorkoutExercise> workoutExercises;

    @OneToMany (mappedBy = "user")
    private List<Split> splits;

}
