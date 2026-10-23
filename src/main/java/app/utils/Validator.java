package app.utils;

import app.entities.Exercise;
import app.enums.BodyParts;
import app.enums.Equipment;
import io.javalin.http.Context;

import java.util.Arrays;

public class Validator {

    public static Exercise validateExercise(Context ctx) {
        return ctx.bodyValidator(Exercise.class)
                .check(e -> e.getName() != null
                        && !e.getName().isBlank(), "Please provide a name.")
                .check(e -> e.getBodyParts() != null
                        && !e.getBodyParts().isEmpty(), "Please provide which body part the exercise trains.")
                .check(e -> e.getBodyParts() != null
                        && e.getBodyParts().stream().allMatch(bp -> bp != null && Arrays.stream(BodyParts.values())
                        .anyMatch(b -> b.getValue().equals(bp.getBodyPart()))), "Invalid body part.")
                .check(e -> e.getEquipments() != null
                        && !e.getEquipments().isEmpty(), "Please provide the equipment needed to perform this exercise")
                .check(e -> e.getEquipments() != null
                        && e.getEquipments().stream().allMatch(eq -> eq != null && Arrays.stream(Equipment.values())
                        .anyMatch(equ -> equ.getValue().equals(eq.getEquipment()))), "Invalid equipment")
                .check(e -> e.getInstructions() != null
                        && !e.getInstructions().isBlank(), "Please provide instructions for the exercise").get();
    }

}
