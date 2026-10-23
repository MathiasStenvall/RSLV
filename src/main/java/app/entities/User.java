package app.entities;

import app.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false, unique = true)
    private String email;

    @Setter(AccessLevel.NONE)
    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String phoneNumber;

    private String sex;

    private int height;

    private double currentWeight;

    private LocalDate signupDate;

    @OneToMany(mappedBy = "user")
    private List<WorkoutExercise> workoutExercises;

    @OneToMany(mappedBy = "user")
    private List<Split> splits;

    public User(String name, int age, String email,
                String password, String phoneNumber, String sex, int height,
                double currentWeight) {
        this.name = name;
        this.age = age;
        this.role = Role.USER;
        this.email = email;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.height = height;
        this.currentWeight = currentWeight;
        this.signupDate = LocalDate.now();
    }

    public boolean checkPassword(String password){
        return BCrypt.checkpw(password, this.password);
    }

    public void changePassword(String newPassword){
        this.password = BCrypt.hashpw(newPassword, BCrypt.gensalt());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && age == user.age && height == user.height && Double.compare(currentWeight, user.currentWeight) == 0 && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(sex, user.sex) && Objects.equals(signupDate, user.signupDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, email, phoneNumber, sex, height, currentWeight, signupDate);
    }
}
