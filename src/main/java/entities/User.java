package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int age;

    private String email;

    private String password;

    private int phoneNumber;

    private String sex;

    private double height;

    private double currentWeight;

    public User(String name, int age, String email, String password, int phoneNumber, String sex, double height, double currentWeight) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.height = height;
        this.currentWeight = currentWeight;
    }
}
