package app.enums;

import lombok.Getter;

@Getter
public enum BodyParts {

    SHOULDERS("shoulders"),
    UPPER_ARMS("upper%20arms"),
    LOWER_ARMS("lower%20arms"),
    CHEST("chest"),
    BACK("back"),
    WAIST("waist"),
    UPPER_LEGS("upper%20legs"),
    LOWER_LEGS("lower%20legs"),
    CARDIO("cardio");

    private final String urlValue;

    BodyParts (String urlValue){
        this.urlValue = urlValue;
    }

}
