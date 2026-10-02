package app.enums;

import lombok.Getter;

@Getter
public enum BodyParts {

    SHOULDERS("shoulders" , "shoulders"),
    UPPER_ARMS("upper arms", "upper%20arms"),
    LOWER_ARMS("lower arms","lower%20arms"),
    CHEST("chest","chest"),
    BACK("back","back"),
    WAIST("waist","waist"),
    UPPER_LEGS("upper legs","upper%20legs"),
    LOWER_LEGS("lower legs","lower%20legs"),
    CARDIO("cardio","cardio"),
    NECK("neck","neck");

    private final String value;
    private final String urlValue;

    BodyParts (String value, String urlValue){
        this.value = value;
        this.urlValue = urlValue;
    }

}
