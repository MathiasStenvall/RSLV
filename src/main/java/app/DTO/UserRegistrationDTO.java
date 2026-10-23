package app.DTO;

public record UserRegistrationDTO (
        String name,
        int age,
        String email,
        String password,
        String phoneNumber,
        String sex,
        int height,
        double currentWeight
)
{}
