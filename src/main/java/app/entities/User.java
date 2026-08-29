package app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    private int phoneNumber;

    private String sex;

    private int height;

    private double currentWeight;

    private LocalDate signupDate;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && age == user.age && phoneNumber == user.phoneNumber && height == user.height && Double.compare(currentWeight, user.currentWeight) == 0 && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(sex, user.sex) && Objects.equals(signupDate, user.signupDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, email, password, phoneNumber, sex, height, currentWeight, signupDate);
    }
}
