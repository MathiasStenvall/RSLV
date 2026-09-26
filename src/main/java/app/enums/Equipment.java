package app.enums;

import lombok.Getter;

@Getter
public enum Equipment {

    STEPMILL_MACHINE("stepmill%20machine"),
    ELLIPTICAL_MACHINE("elliptical%20machine"),
    TRAP_BAR("trap%20bar"),
    STATIONARY_BIKE("stationary%20bike"),
    SMITH_MACHINE("smith%20machine"),
    EZ_BARBELL("ez%20barbell"),
    DUMBBELL("dumbbell"),
    ROPE("rope"),
    BARBELL("barbell"),
    LEVERAGE_MACHINE("leverage%20machine"),
    CABLE("cable"),
    BODY_WEIGHT("body%20weight");

    private final String urlValue;

    Equipment (String urlValue){
        this.urlValue = urlValue;
    }

}
