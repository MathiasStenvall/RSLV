package app.enums;

import lombok.Getter;

@Getter
public enum Equipment {

    STEPMILL_MACHINE("stepmill machine","stepmill%20machine"),
    ELLIPTICAL_MACHINE("elliptical machine","elliptical%20machine"),
    TRAP_BAR("trap bar","trap%20bar"),
    STATIONARY_BIKE("stationary bike","stationary%20bike"),
    SMITH_MACHINE("smith machine","smith%20machine"),
    EZ_BAR("EZ bar","ez%20bar"),
    DUMBBELL("dumbbell","dumbbell"),
    ROPE("rope","rope"),
    BARBELL("barbell","barbell"),
    LEVERAGE_MACHINE("leverage machine","leverage%20machine"),
    CABLE("cable","cable"),
    BODYWEIGHT("bodyweight","bodyweight"),
    ASSISTED("assisted", "assisted"),
    RESISTANCE_BAND("resistance band", "resistance%20band"),
    ROLLER("roller", "roller"),
    WEIGHTED("weighted", "weighted"),
    SLED_MACHINE("sled machine", "sled%20machine"),
    STABILITY_BALL("stability ball", "stability%20ball"),
    BOSU_BALL("bosu ball", "bosu%20ball"),
    KETTLEBELL("kettlebell", "kettlebell"),
    SUSPENSION_TRAINER("suspension trainer", "suspension%20trainer"),
    MEDICINE_BALL("medicine ball", "medicine%20ball"),
    TOWEL("towel", "towel"),
    TENNIS_BALL("tennis ball", "tennis%20ball"),
    AB_WHEEL("ab wheel","ab%20wheel"),
    OLYMPIC_BARBELL("olympic barbell", "olympic%20barbell"),
    TIRE("tire", "tire"),
    UPPER_BODY_ERGOMETER("upper body ergometer", "upper%20body%20ergometer"),
    HAMMER("hammer", "hammer"),
    SKI_ERGOMETER("ski ergometer", "ski%20ergometer");


    private final String value;
    private final String urlValue;

    Equipment (String value, String urlValue){
        this.value = value;
        this.urlValue = urlValue;
    }

}
