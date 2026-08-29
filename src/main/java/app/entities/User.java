package app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Builder
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

    private int height;

    private double currentWeight;

    private LocalDate signupDate;

}
