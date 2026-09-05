package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && age == user.age && height == user.height && Double.compare(currentWeight, user.currentWeight) == 0 && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(sex, user.sex) && Objects.equals(signupDate, user.signupDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, email, password, phoneNumber, sex, height, currentWeight, signupDate);
    }
}
